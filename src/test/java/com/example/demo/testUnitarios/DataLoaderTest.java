package com.example.demo.testUnitarios;

import com.example.demo.model.ProductoModel;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.controller.DataLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class DataLoaderTest {

    private ProductoRepository repository;
    private DataLoader dataLoader;

    @BeforeEach
    void setUp() {
        repository = mock(ProductoRepository.class);
        dataLoader = new DataLoader(repository);
    }

 @Test
void testRun_WhenDatabaseIsEmpty_ShouldSaveProducts() throws Exception {
    when(repository.count()).thenReturn(0L);

    ArgumentCaptor<List<ProductoModel>> captor = ArgumentCaptor.forClass(List.class);

    dataLoader.run();

    verify(repository).saveAll(captor.capture());
    
    List<ProductoModel> listaCapturada = captor.getValue();

    assertEquals(5, listaCapturada.size());
    assertEquals("Pastel de Chocolate", listaCapturada.get(0).getName());
    assertEquals("selva_negra.webp", listaCapturada.get(4).getImagenUrl().substring(listaCapturada.get(4).getImagenUrl().lastIndexOf("/") + 1));
}

    @Test
    void testRun_WhenDatabaseIsNotEmpty_ShouldNotSaveAnything() throws Exception {
        when(repository.count()).thenReturn(5L);

        dataLoader.run();

        verify(repository, never()).saveAll(anyList());
        verify(repository, times(1)).count();
    }
}