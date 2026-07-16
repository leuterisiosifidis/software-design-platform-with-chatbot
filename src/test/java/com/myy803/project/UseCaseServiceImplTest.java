package com.myy803.project;                                                                       
                  
  import static org.mockito.Mockito.verify;
  import static org.mockito.Mockito.when;

  import java.util.Arrays;
  import java.util.List;
  import java.util.Optional;                                                                        
   
  import org.junit.jupiter.api.Assertions;                                                          
  import org.junit.jupiter.api.Test;
  import org.junit.jupiter.api.extension.ExtendWith;
  import org.mockito.InjectMocks;
  import org.mockito.Mock;
  import org.mockito.junit.jupiter.MockitoExtension;                                                
   
  import com.myy803.project.domain.Project;                                                         
  import com.myy803.project.domain.UseCase;
  import com.myy803.project.repositories.UseCaseRepository;
  import com.myy803.project.services.UseCaseServiceImpl;                                            
   
  @ExtendWith(MockitoExtension.class)                                                               
  public class UseCaseServiceImplTest {

      @Mock
      private UseCaseRepository useCaseRepository;
                                                                                                    
      @InjectMocks
      private UseCaseServiceImpl useCaseService;                                                    
                  
      @Test
      public void findByProject_ShouldReturnUseCaseList() {
          Project project = new Project();
          UseCase uc1 = new UseCase();                                                              
          UseCase uc2 = new UseCase();
          List<UseCase> useCases = Arrays.asList(uc1, uc2);                                         
                  
          when(useCaseRepository.findByProject(project)).thenReturn(useCases);                      
   
          List<UseCase> result = useCaseService.findByProject(project);                             
                  
          Assertions.assertEquals(2, result.size());
          verify(useCaseRepository).findByProject(project);
      }

      @Test                                                                                         
      public void findById_ShouldReturnUseCase() {
          UseCase uc = new UseCase();                                                               
                  
          when(useCaseRepository.findById(1)).thenReturn(Optional.of(uc));

          UseCase result = useCaseService.findById(1);                                              
  
          Assertions.assertNotNull(result);                                                         
          verify(useCaseRepository).findById(1);
      }

      @Test
      public void findById_WhenNotFound_ShouldReturnNull() {
          when(useCaseRepository.findById(1)).thenReturn(Optional.empty());
                                                                                                    
          UseCase result = useCaseService.findById(1);
                                                                                                    
          Assertions.assertNull(result);
          verify(useCaseRepository).findById(1);
      }

      @Test
      public void saveUseCase_ShouldCallRepositorySave() {
          UseCase uc = new UseCase();                                                               
  
          useCaseService.saveUseCase(uc);                                                           
                  
          verify(useCaseRepository).save(uc);
      }

      @Test                                                                                         
      public void deleteUseCase_ShouldCallRepositoryDeleteById() {
          useCaseService.deleteUseCase(1);                                                          
                  
          verify(useCaseRepository).deleteById(1);
      }

 }
