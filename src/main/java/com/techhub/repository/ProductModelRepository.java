package com.techhub.repository;

import com.techhub.entity.Product;
import com.techhub.entity.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductModelRepository extends PagingAndSortingRepository<ProductModel, Long> {
    ProductModel findFirstByProductOrderByPrice(Product product);
}
