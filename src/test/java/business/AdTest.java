package business;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdTest {
    @Test
    void ad_priceShouldMatch() {
        Ad ad = new Ad();
        ad.setPrice(100.00);
        assertEquals(100.00, ad.returnPrice());
    }

    @Test
    void ad_priceShouldNotMatch() {
        Ad ad = new Ad();
        ad.setPrice(900.00);
        assertEquals(100.00, ad.returnPrice());
    }
}