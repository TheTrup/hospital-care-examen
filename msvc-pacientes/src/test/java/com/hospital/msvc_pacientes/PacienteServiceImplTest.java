package com.hospital.msvc_pacientes;

import com.hospital.msvc_pacientes.dto.PacienteDTO;
import com.hospital.msvc_pacientes.model.Paciente;
import com.hospital.msvc_pacientes.repository.PacienteRepository;
import com.hospital.msvc_pacientes.service.PacienteServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceImplTest {

    @Mock
    private PacienteRepository repository; // Simular dependencia "repository"

    @InjectMocks
    private PacienteServiceImpl pacienteService; // Inyecta el simulador en tu implementación real

    @Test
    @DisplayName("Debería retornar un PacienteDTO cuando se busca por un ID existente")
    void testObtenerPacientePorIdExitoso() {
        // 1. GIVEN (Preparación Builder)
        Paciente pacienteMock = Paciente.builder()
                .id(1L)
                .rut("12345678-9")
                .nombre("Pepito")
                .apellido("Pérez")
                .email("pepito@gmail.com")
                .telefono("912345678")
                .build();

       
        when(repository.findById(1L)).thenReturn(Optional.of(pacienteMock));

        // 2. WHEN (Ejecución del metodo)
        PacienteDTO resultado = pacienteService.obtenerPorId(1L);

        // 3. THEN (Asegurar que la logica funciona wacho)
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals("Pepito", resultado.getNombre(), "El nombre debería coincidir");
        assertEquals("Pérez", resultado.getApellido(), "El apellido debería coincidir");
        assertEquals("12345678-9", resultado.getRut(), "El RUT debería coincidir");
        assertEquals(1L, resultado.getId());

        
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException cuando el ID del paciente no existe")
    void testObtenerPacientePorIdNoEncontrado() {
        // 1. GIVEN (La BD simulada devuelve un Optional vacío)
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // 2. WHEN & THEN (Ejecutamos esperando que arroje el error controlado)
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacienteService.obtenerPorId(99L);
        });

        // Verificamos que el mensaje del error sea el correcto que definiste en tu Service
        assertEquals("Paciente no encontrado con el ID especificado.", exception.getMessage());
        
        // Verificamos que se haya llamado a la consulta
        verify(repository, times(1)).findById(99L);
    }
}