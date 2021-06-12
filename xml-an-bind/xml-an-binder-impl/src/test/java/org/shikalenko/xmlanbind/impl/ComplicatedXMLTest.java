package org.shikalenko.xmlanbind.impl;

import static org.junit.Assert.*;
import static org.shikalenko.xmlanbind.XMLAnnotationBinder.FEATURE_XPATH;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.shikalenko.xmlanbind.XMLAnnotationBinder;
import org.shikalenko.xmlanbind.annotations.XPath;
import org.xml.sax.InputSource;

public class ComplicatedXMLTest extends TstBase {

    private static final String NS_PREFIX = "shp";
    private static final String NS_URI = "urn:types.partner.api.shopping.com";
    private static final long NICON_ID = 101677489;

    public static class Products {
        private Map<Long, Product> products = new LinkedHashMap<>();
        @XPath("//shp:product")
        public Product addProduct(Product product) {
            return products.put(product.getId(), product);
        }
        public Product getProduct(long id) {
            return products.get(id);
        }
    }

    public static class Product {
        private long id;
        private String name;
        private String shortDescription;
        private String fullDescription;
        private int reviewCount;
        private float rating;
        private float minPrice;
        private float maxPrice;
        private float basePrice;
        public long getId() {
            return id;
        }
        public void setId(long id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getShortDescription() {
            return shortDescription;
        }
        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }
        public String getFullDescription() {
            return fullDescription;
        }
        public void setFullDescription(String fullDescription) {
            this.fullDescription = fullDescription;
        }
        public int getReviewCount() {
            return reviewCount;
        }
        @XPath("shp:rating/shp:reviewCount")
        public void setReviewCount(int reviewCount) {
            this.reviewCount = reviewCount;
        }
        public float getRating() {
            return rating;
        }
        @XPath("shp:rating/shp:rating")
        public void setRating(float rating) {
            this.rating = rating;
        }
        public float getMinPrice() {
            return minPrice;
        }
        public void setMinPrice(float minPrice) {
            this.minPrice = minPrice;
        }
        public float getMaxPrice() {
            return maxPrice;
        }
        public void setMaxPrice(float maxPrice) {
            this.maxPrice = maxPrice;
        }
        public float getBasePrice() {
            return basePrice;
        }
        @XPath("(.//shp:basePrice + .//shp:originalPrice) div 2")
        public void setBasePrice(float basePrice) {
            this.basePrice = basePrice;
        }
        
        
    }


    @Before
    public void setUp() throws Exception {
        binder = XMLAnnotationBinder.newInstance();
        binder.setFeature(FEATURE_XPATH);
        binder.getXPathContext().addNamespaceMapping(NS_PREFIX, NS_URI);
    }

    //@Test
    public void test_() throws Exception {
        
    }
    
    @Test
    public void test() throws Exception {
        File file = new File("data", "shopping.xml");
        InputSource inputSource = new InputSource(file.getAbsolutePath());
        Products products;
        try (InputStream inputStream = new FileInputStream(file)) {
            try (BufferedInputStream byteStream = new BufferedInputStream(inputStream)) {
                inputSource.setByteStream(byteStream);
                products = binder.bind(Products.class, inputSource);
            }
        }
        assertNotNull(products);
        Product nikon = products.getProduct(NICON_ID);
        assertNotNull("Nikon must be exist", nikon);
        assertEquals("Nikon ID", NICON_ID, nikon.getId());
        assertEquals("Nikon Name", "Nikon D3100 Digital Camera", nikon.getName());
        assertEquals("Nikon ShortDescription", 
                "14.2 Megapixel, SLR Camera, 3 in. LCD Screen, With High Definition Video, Weight: 1 lb.", 
                nikon.getShortDescription());
        assertEquals("Nikon FullDescription", 
                "The Nikon D3100 digital SLR camera speaks to the growing ranks of enthusiastic D-SLR users and aspiring photographers by providing an easy-to-use and affordable entrance to the world of Nikon D-SLR’s. The 14.2-megapixel D3100 has powerful features, such as the enhanced Guide Mode that makes it easy to unleash creative potential and capture memories with still images and full HD video. Like having a personal photo tutor at your fingertips, this unique feature provides a simple graphical interface on the camera’s LCD that guides users by suggesting and/or adjusting camera settings to achieve the desired end result images. The D3100 is also the world’s first D-SLR to introduce full time auto focus (AF) in Live View and D-Movie mode to effortlessly achieve the critical focus needed when shooting Full HD 1080p video.", 
                nikon.getFullDescription());
        assertEquals("Nikon ReviewCount", 9, nikon.getReviewCount());
        assertEquals("Nikon Rating", 4.56, nikon.getRating(), 0.000001);
        assertEquals("Nikon MinPrice", 429.00, nikon.getMinPrice(), 0.000001);
        assertEquals("Nikon MaxPrice", 1360.00, nikon.getMaxPrice(), 0.000001);
        assertEquals("Nikon BasePrice", (529.00 + 799.00)/2, nikon.getBasePrice(), 0.000001);
    }


}
