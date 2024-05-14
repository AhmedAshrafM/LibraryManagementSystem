package org.maidscc.librarymanagementsystem.patron;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maidscc.librarymanagementsystem.exceptions.DuplicateFoundException;
import org.maidscc.librarymanagementsystem.exceptions.PatronNotFoundException;
import org.maidscc.librarymanagementsystem.models.Patron;
import org.maidscc.librarymanagementsystem.repositories.PatronRepository;
import org.maidscc.librarymanagementsystem.services.PatronService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this); // Initialize the annotated fields
    }

    @Test
    public void testGetAllPatrons() {
        // Arrange
        Patron patron1 = new Patron();
        patron1.setId(1L);
        Patron patron2 = new Patron();
        patron2.setId(2L);
        when(patronRepository.findAll()).thenReturn(List.of(patron1, patron2));

        // Act
        List<Patron> patrons = patronService.getAllPatrons();

        // Assert
        assertEquals(2, patrons.size());
        verify(patronRepository, times(1)).findAll();
    }

    @Test
    public void testGetPatronById() {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));

        // Act
        Patron returnedPatron = patronService.getPatronById(1L);

        // Assert
        assertEquals(patron.getId(), returnedPatron.getId());
        verify(patronRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddPatron() {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setEmail("john@example.com");
        patron.setAddress("123 Main St");
        patron.setPhone("1234567890");
        when(patronRepository.findByEmail(patron.getEmail())).thenReturn(Optional.empty());
        when(patronRepository.save(patron)).thenReturn(patron);

        // Act
        Patron returnedPatron = patronService.addPatron(patron);

        // Assert
        assertEquals(patron.getId(), returnedPatron.getId());
        verify(patronRepository, times(1)).findByEmail(patron.getEmail());
        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    public void testAddPatron_DuplicateEmail() {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setEmail("john@example.com");
        patron.setAddress("123 Main St");
        patron.setPhone("1234567890");
        when(patronRepository.findByEmail(patron.getEmail())).thenReturn(Optional.of(patron));

        // Act + Assert
        assertThrows(DuplicateFoundException.class, () -> patronService.addPatron(patron));
        verify(patronRepository, times(1)).findByEmail(patron.getEmail());
        verify(patronRepository, never()).save(patron);
    }

    @Test
    public void testUpdatePatron() {
        // Arrange
        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("John Doe");
        patron.setEmail("john@example.com");
        patron.setAddress("123 Main St");
        patron.setPhone("1234567890");
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(patronRepository.findByEmail(patron.getEmail())).thenReturn(Optional.empty());
        when(patronRepository.save(patron)).thenReturn(patron);

        // Act
        Patron returnedPatron = patronService.updatePatron(1L, patron);

        // Assert
        assertEquals(patron.getId(), returnedPatron.getId());
        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).findByEmail(patron.getEmail());
        verify(patronRepository, times(1)).save(patron);
    }

    @Test
    public void testUpdatePatron_DuplicateEmail() {
        // Arrange
        Patron existingPatron = new Patron();
        existingPatron.setId(1L);
        existingPatron.setName("John Doe");
        existingPatron.setEmail("john@example.com");
        existingPatron.setAddress("Some address");
        existingPatron.setPhone("1234567890");
        Patron updatedPatron = new Patron();
        updatedPatron.setId(1L);
        updatedPatron.setName("John Smith");
        updatedPatron.setEmail("john@example.com");
        updatedPatron.setAddress("Some address");
        updatedPatron.setPhone("0987654321");
        when(patronRepository.findById(1L)).thenReturn(Optional.of(existingPatron));
        when(patronRepository.findByEmail(updatedPatron.getEmail())).thenReturn(Optional.of(existingPatron));

        // Act + Assert
        assertThrows(DuplicateFoundException.class, () -> patronService.updatePatron(1L, updatedPatron));
        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).findByEmail(updatedPatron.getEmail());
        verify(patronRepository, never()).save(updatedPatron);
    }

    @Test
    public void testUpdatePatron_NotFound() {
        // Arrange
        Patron updatedPatron = new Patron();
        updatedPatron.setId(1L);
        updatedPatron.setName("John Smith");
        updatedPatron.setEmail("john@example.com");
        updatedPatron.setAddress("Some Address");
        updatedPatron.setPhone("0987654321");
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(PatronNotFoundException.class, () -> patronService.updatePatron(1L, updatedPatron));
        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, never()).findByEmail(updatedPatron.getEmail());
        verify(patronRepository, never()).save(updatedPatron);
    }

    @Test
    public void testDeletePatron() {
        // Arrange
        when(patronRepository.findById(1L)).thenReturn(Optional.of(new Patron()));

        // Act
        patronService.deletePatron(1L);

        // Assert
        verify(patronRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetPatronById_NotFound() {
        // Arrange
        when(patronRepository.findById(1L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(PatronNotFoundException.class, () -> patronService.getPatronById(1L));
        verify(patronRepository, times(1)).findById(1L);
    }
}
