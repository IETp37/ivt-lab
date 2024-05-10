package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockingDetails;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mock1 = mock(TorpedoStore.class);
  private TorpedoStore mock2 = mock(TorpedoStore.class);

  @BeforeEach
  public void init(){
    this.ship = new GT4500(mock1, mock2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mock1.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mock1, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mock1.fire(1)).thenReturn(true);
    when(mock2.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mock1, times(1)).fire(1);
    verify(mock2, times(1)).fire(1);
  }

  ////Unit tests
  @Test
  public void fireTorpedo_Single_Alternating_Success(){
    Mockito.reset(mock1);
    Mockito.reset(mock2);
    when(mock1.fire(1)).thenReturn(true);
    when(mock2.fire(1)).thenReturn(true);

    boolean result = this.ship.fireTorpedo(FiringMode.SINGLE) && this.ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, result);
    verify(mock1, times(1)).fire(1);
    verify(mock2, times(1)).fire(1);
    
  }

  @Test
  public void fireTorpedo_All_SecondEmpty_Failure(){
    Mockito.reset(mock1);
    Mockito.reset(mock2);
    when(mock1.fire(1)).thenReturn(true);
    when(mock2.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(false, result);
    verify(mock1, times(1)).fire(1);
    verify(mock2, times(1)).fire(1);
    
  }

  @Test
  public void fireTorpedo_All_PrimaryEmpty_Failure(){
    Mockito.reset(mock1);
    Mockito.reset(mock2);
    when(mock1.fire(1)).thenReturn(false);
    // when(mock2.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.ALL);

    assertEquals(false, result);
    verify(mock1, times(1)).fire(1);
    verify(mock2, times(1)).fire(1);
    
  }

  @Test
  public void fireTorpedo_Single_PrimaryBecomesEmpty_Failure(){
    Mockito.reset(mock1);
    Mockito.reset(mock2);
    when(mock1.fire(1)).thenReturn(true).thenReturn(false);
    when(mock2.fire(1)).thenReturn(true);

    boolean result = true;
    for(int i = 0; i < 3; i++) result = result && ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);
    verify(mock1, times(2)).fire(1);
    verify(mock2, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_SecondEmpty_Failure(){
    Mockito.reset(mock1);
    Mockito.reset(mock2);
    when(mock1.fire(1)).thenReturn(true);
    when(mock2.fire(1)).thenReturn(false);

    boolean result = ship.fireTorpedo(FiringMode.SINGLE) && ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, result);
    verify(mock1, times(1)).fire(1);
    verify(mock2, times(1)).fire(1);

  }
}
