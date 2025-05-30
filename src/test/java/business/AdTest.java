package business;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdTest {
    @Test
    void testAd() {
        Ad ad = new Ad();
        ad.setPrice(100.00);
        assertEquals(100.00, ad.returnPrice());
    }
}