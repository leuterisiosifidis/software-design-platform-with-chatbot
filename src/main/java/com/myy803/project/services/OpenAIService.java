package com.myy803.project.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class OpenAIService {

    @Value("${groq.api-key}")
    private String apiKey;

    @Value("${groq.api-url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String ask(String userMessage) {
        return ask(userMessage, null);
    }

    public String ask(String userMessage, String currentPage) {
        try {
            if (userMessage == null || userMessage.trim().isEmpty()) {
                return "Please type a message first.";
            }

            String normalizedMessage = normalize(userMessage);
            String normalizedPage = normalize(currentPage);

            // 1) Rules ΜΟΝΟ για πολύ βασικές navigation ερωτήσεις
            String quickReply = getBasicNavigationReply(normalizedMessage);
            if (quickReply != null) {
                return quickReply;
            }

            // 2) Αν η ερώτηση δεν είναι σχετική ούτε με το site ούτε με τις έννοιες του project, κόψ' την
            if (!isProjectRelatedQuestion(normalizedMessage)) {
                return "Μπορώ να βοηθήσω με τη χρήση της ιστοσελίδας και με έννοιες που σχετίζονται με αυτή, όπως Projects, Use Cases και CRC Cards.";
            }

            // 3) Για όλες τις υπόλοιπες σχετικές ερωτήσεις, κάλεσε το LLM
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            String systemPrompt = buildSystemPrompt(normalizedPage);

            Map<String, Object> body = Map.of(
                    "model", model,
                    "temperature", 0.2,
                    "messages", List.of(
                            Map.of(
                                    "role", "system",
                                    "content", systemPrompt
                            ),
                            Map.of(
                                    "role", "user",
                                    "content", userMessage.trim()
                            )
                    )
            );

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getBody() == null) {
                return "Δεν έλαβα απάντηση από το chatbot.";
            }

            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode choices = root.path("choices");

            if (!choices.isArray() || choices.isEmpty()) {
                return "Δεν βρέθηκε απάντηση από το chatbot.";
            }

            String content = choices.get(0)
                    .path("message")
                    .path("content")
                    .asText();

            if (content == null || content.trim().isEmpty()) {
                return "Δεν βρέθηκε απάντηση από το chatbot.";
            }

            return content.trim();

        } catch (RestClientResponseException e) {
            return "Η υπηρεσία chatbot επέστρεψε σφάλμα.";
        } catch (Exception e) {
            return "Παρουσιάστηκε σφάλμα κατά την επικοινωνία με το chatbot.";
        }
    }

    private String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", " ")
                .trim();
    }

    /**
     * Rules μόνο για πολύ βασικές, επαναλαμβανόμενες navigation ερωτήσεις.
     * Ό,τι είναι πιο γενικό ή εννοιολογικό πρέπει να πάει στο LLM.
     */
    private String getBasicNavigationReply(String msg) {

        // CRC Cards - μόνο πλοήγηση
        if (containsAny(msg, "crc", "crc card", "crc cards", "crc cards", "κάρτες crc", "crc καρτες")
                && containsAny(msg, "που", "πού", "where", "βρω", "βλέπω", "δω", "see", "find", "view")) {
            return "Για να δεις τα CRC Cards, από το Dashboard πάτησε \"My Projects\" και μετά πάτησε \"View CRC Cards\" δίπλα στο project που θέλεις.";
        }

        // Use Cases - μόνο πλοήγηση
        if (containsAny(msg, "use case", "use cases", "usecase", "usecases", "περιπτώσεις χρήσης", "περιπτωσεις χρησης")
                && containsAny(msg, "που", "πού", "where", "βρω", "βλέπω", "δω", "see", "find", "view")) {
            return "Για να δεις τα Use Cases, από το Dashboard πάτησε \"My Projects\" και μετά πάτησε \"View Use Cases\" δίπλα στο project που θέλεις.";
        }

        // Profile - βασική πλοήγηση
        if (containsAny(msg, "profile", "προφιλ", "προφίλ")
                && containsAny(msg, "που", "πού", "where", "βρω", "βλέπω", "δω", "find", "see", "edit", "επεξεργαστώ", "αλλάξω")) {
            return "Για να επεξεργαστείς το προφίλ σου, από το Dashboard πάτησε \"Edit Profile\".";
        }

        // Projects - βασική πλοήγηση
        if (containsAny(msg, "project", "projects", "εργο", "έργο", "εργα", "έργα")
                && containsAny(msg, "που", "πού", "where", "βρω", "βλέπω", "δω", "see", "find", "view")) {
            return "Για να δεις τα Projects σου, από το Dashboard πάτησε \"My Projects\".";
        }

        // Logout
        if (containsAny(msg, "logout", "log out", "αποσύνδεση", "αποσυνδεση", "βγω", "exit")) {
            return "Για να κάνεις logout, από το Dashboard πάτησε \"Logout\".";
        }

        // Login
        if (containsAny(msg, "login", "log in", "συνδεση", "σύνδεση", "μπω", "είσοδος")) {
            return "Για να κάνεις login, πήγαινε στη σελίδα σύνδεσης, συμπλήρωσε username και password και πάτησε \"Login\".";
        }

        // Register
        if (containsAny(msg, "register", "registration", "εγγραφη", "εγγραφή", "create account", "λογαριασμό", "λογαριασμο")) {
            return "Για να δημιουργήσεις λογαριασμό, πήγαινε στη σελίδα Register, συμπλήρωσε τα στοιχεία σου και πάτησε \"Register\".";
        }

        return null;
    }

    /**
     * Πιο χαλαρό φίλτρο:
     * Επιτρέπει όχι μόνο navigation ερωτήσεις,
     * αλλά και γενικότερες σχετικές ερωτήσεις για τις έννοιες του project.
     */
    private boolean isProjectRelatedQuestion(String msg) {
        return containsAny(msg,
                // pages / navigation
                "site", "website", "ιστοσελιδα", "ιστοσελίδα", "page", "σελιδα", "σελίδα",
                "button", "κουμπι", "κουμπί", "link", "φορμα", "φόρμα",
                "dashboard", "profile", "login", "register", "logout",

                // core app entities
                "project", "projects", "εργο", "έργο", "εργα", "έργα",
                "use case", "use cases", "usecase", "usecases", "περιπτωση χρησης", "περίπτωση χρήσης",
                "περιπτωσεις χρησης", "περιπτώσεις χρήσης",
                "crc", "crc card", "crc cards", "κάρτα crc", "κάρτες crc",

                // related concepts that should also be allowed
                "τι ειναι", "τι είναι", "what is", "difference", "διαφορα", "διαφορά",
                "πως βοηθα", "πώς βοηθά", "help in project", "purpose", "χρησιμευει", "χρησιμεύει",
                "responsibility", "responsibilities", "collaboration", "collaborations",
                "actor", "actors", "precondition", "preconditions", "main flow", "alternative flow", "postconditions",
                "class", "κλαση", "κλάση", "software analysis", "analysis", "requirements"
        );
    }

    private String buildSystemPrompt(String currentPage) {
        String pageInfo = (currentPage == null || currentPage.isBlank())
                ? "Unknown current page."
                : "Current page: " + currentPage + ".";

        return """
                You are an assistant for a specific student project web application.

                Your role is to help in TWO ways:
                1. Answer navigation and usage questions about this website.
                2. Answer broader but still relevant concept questions about entities used in this application,
                   such as Projects, Use Cases, and CRC Cards.

                Important rules:
                - Stay related to this application and its domain.
                - If the user asks something completely unrelated to the website or its concepts, politely say that
                  you can only help with this website and related concepts used in it.
                - Do not invent pages, buttons, or features that do not exist.
                - For navigation questions, give short step-by-step answers using the exact button/link names when possible.
                - For concept questions (for example "What is a CRC Card?" or "How can CRC Cards help in a project?"),
                  answer clearly and briefly, and connect the explanation to how the concept is used inside this application.
                - If useful, combine both concept explanation and navigation guidance.
                - Keep answers practical, concise, and easy to understand.

                Website structure:
                - Dashboard page:
                  - "Edit Profile"
                  - "My Projects"
                  - "Logout"
                  - Chatbot panel
                - Projects page:
                  - shows user's projects
                  - create project form
                  - "View Use Cases"
                  - "View CRC Cards"
                  - "Delete"
                  - "Back to Dashboard"
                - Use Cases page:
                  - add use case form
                  - existing use cases list
                  - "Edit"
                  - "Delete"
                  - "Back to Projects"
                - Edit Use Case page:
                  - user can edit name, actors, preconditions, main flow, alternative flow, postconditions
                - CRC Cards page:
                  - add CRC card form
                  - existing CRC cards list
                  - "Edit"
                  - "Delete"
                  - "Back to Projects"
                - Edit CRC Card page:
                  - user can edit class name, responsibilities, collaborations
                  - user can link the CRC Card to Use Cases with checkboxes
                  - "Save Changes"
                  - "Cancel"
                - Profile page:
                  - user can edit first name, last name, email, password
                  - "Save Changes"
                  - "Back to Dashboard"

                Domain facts inside this application:
                - A Project contains Use Cases and CRC Cards.
                - Use Cases describe actors, preconditions, main flow, alternative flow, and postconditions.
                - CRC Cards describe a class with responsibilities and collaborations.
                - CRC Cards can be linked to Use Cases in this application.
                - Users manage projects from the Projects page.

                Helpful navigation facts:
                - To see CRC Cards: Dashboard -> "My Projects" -> "View CRC Cards"
                - To see Use Cases: Dashboard -> "My Projects" -> "View Use Cases"
                - To edit profile: Dashboard -> "Edit Profile"

                """ + pageInfo;
    }

    private boolean containsAny(String text, String... keywords) {
        if (text == null || text.isBlank()) {
            return false;
        }
        for (String keyword : keywords) {
            if (text.contains(keyword.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }
}