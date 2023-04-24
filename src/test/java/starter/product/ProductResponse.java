package starter.product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ProductResponse {
    private List<Product> productList;
    private Detail detail;

    @Getter
    @Setter
    public static class Product {
        private String provider;
        private String title;
        private String url;
        private String brand;
        private double price;
        private String unit;
        private Boolean isPromo;
        private String promoDetails;
        private String image;

        @Override
        public boolean equals(Object o) {
            Product product = (Product) o;
            return (!Objects.nonNull(product.provider) || product.provider.contentEquals(this.provider)) && (!Objects.nonNull(product.title) || product.title.contentEquals(this.title)) && (!Objects.nonNull(product.url) || product.url.contentEquals(this.url)) && (!Objects.nonNull(product.brand) || product.brand.contentEquals(this.brand)) && product.price == this.price && (!Objects.nonNull(product.unit) || product.unit.contentEquals(this.unit)) && (!Objects.nonNull(product.isPromo) || product.isPromo.equals(this.isPromo)) && (!Objects.nonNull(product.promoDetails) || product.promoDetails.contentEquals(this.promoDetails)) && (!Objects.nonNull(product.image) || product.image.contentEquals(this.image));
        }

        @Override
        public String toString() {
            return ("provider : " + this.getProvider() + ", title : " + this.getTitle() + ", url : " + this.getUrl() + ", brand : " + this.getBrand() + ", price : " + this.getPrice() + ", unit : " + this.getUnit() + ", isPromo : " + this.getIsPromo() + ", promoDetails : " + this.getPromoDetails() + ", image : " + this.getImage());
        }
    }

    @Getter
    @Setter
    public static class Detail {
        private Boolean error;
        private String message;
        private String requested_item;
        private String served_by;

        @Override
        public boolean equals(Object o) {
            Detail detail = (Detail) o;
            return (!Objects.nonNull(detail.error) || detail.error.equals(this.error)) && (!Objects.nonNull(detail.message) || detail.message.contentEquals(this.message)) && (!Objects.nonNull(detail.requested_item) || detail.requested_item.contentEquals(this.requested_item)) && (!Objects.nonNull(detail.served_by) || detail.served_by.contentEquals(this.served_by));
        }

        @Override
        public String toString() {
            return ("error : " + this.getError() + ", message : " + this.getMessage() + ", requested_item : " + this.getRequested_item() + ", server : " + this.getServed_by());
        }
    }
}
