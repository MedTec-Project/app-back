package br.medtec.services;

import br.medtec.repositories.MedicamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Teste Medicamento")
@ExtendWith(MockitoExtension.class)
public class MedicamentoServiceTest {

    @InjectMocks
    MedicamentoService medicamentoService;

    @Mock
    MedicamentoRepository medicamentoRepository;
//
//    @Test
//    @DisplayName("Cadastro de medicamento com sucesso")

}
