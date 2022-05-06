package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;

  private  TorpedoStore primaryTorpedoStore; 
  private  TorpedoStore secondaryTorpedoStore;

  @BeforeEach
  public void init(){
    primaryTorpedoStore = mock(TorpedoStore.class);
    secondaryTorpedoStore = mock(TorpedoStore.class);

    when(primaryTorpedoStore.getTorpedoCount()).thenReturn(10); 
    when(secondaryTorpedoStore.getTorpedoCount()).thenReturn(10);

    this.ship = new GT4500(primaryTorpedoStore, secondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryTorpedoStore, times(1)).isEmpty(); 
    verify(primaryTorpedoStore, times(1)).fire(1); 
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);

    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primaryTorpedoStore, times(1)).isEmpty(); 
    verify(primaryTorpedoStore, times(1)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).isEmpty(); 
    verify(secondaryTorpedoStore, times(1)).fire(1); 
    assertEquals(true, result);
  } 

  @Test
  public void fireTorpedo_Single_First_Fail(){
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);

    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryTorpedoStore, times(1)).isEmpty(); 
    verify(primaryTorpedoStore, times(0)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).isEmpty(); 
    verify(secondaryTorpedoStore, times(1)).fire(1); 
    assertEquals(true, result);
  } 

  @Test
  public void fireTorpedo_Single_Second_Empty(){
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);

    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primaryTorpedoStore, times(1)).isEmpty();  
    verify(primaryTorpedoStore, times(1)).fire(1); 
    assertEquals(false, result);
  }

  @Test
  public void fireTorpedo_All_First_Fail(){
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);

    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primaryTorpedoStore, times(1)).isEmpty();  
    verify(secondaryTorpedoStore, times(1)).isEmpty(); 
    verify(secondaryTorpedoStore, times(1)).fire(1); 
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_All_Fail(){
    // Arrange
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);

    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primaryTorpedoStore, times(1)).isEmpty(); 
    verify(primaryTorpedoStore, times(0)).fire(1); 
    verify(secondaryTorpedoStore, times(1)).isEmpty(); 
    verify(secondaryTorpedoStore, times(0)).fire(1); 
    assertEquals(false, result);
  } 
}
