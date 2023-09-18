package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.PageDTO;
import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductReq;
import com.team2.fsoft.Ecommerce.dto.request.ProductReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.ProductDetailResponse;
import com.team2.fsoft.Ecommerce.dto.response.ProductRes;
import com.team2.fsoft.Ecommerce.entity.Category;
import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.mapper.impl.ProductDetailResponseMapper;
import com.team2.fsoft.Ecommerce.repository.CustomProductRepository;
import com.team2.fsoft.Ecommerce.repository.ProductRepository;
import com.team2.fsoft.Ecommerce.repository.ShopRepository;

import com.team2.fsoft.Ecommerce.mapper.impl.ProductDetailMapper;
import com.team2.fsoft.Ecommerce.repository.*;
import com.team2.fsoft.Ecommerce.service.ProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @PersistenceContext
    private EntityManager entityManager;
    final ProductDetailMapper productDetailMapper;
    final ShopRepository shopRepository;

    final ProductRepository productRepository;


    final CategoryRepository categoryRepository;

    final ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductDetailResponseMapper productDetailResponseMapper;

    public ProductServiceImpl(ProductDetailMapper productDetailMapper, ShopRepository shopRepository, ProductRepository productRepository,CategoryRepository categoryRepository,ProductDetailRepository productDetailRepository) {
        this.productDetailMapper = productDetailMapper;
        this.shopRepository = shopRepository;
        this.productRepository = productRepository;
        this.categoryRepository=categoryRepository;
        this.productDetailRepository=productDetailRepository;
    }

    @Override
    @Transactional
    public MessagesResponse save(ProductReq productReq) {
        Product product = new Product();
        product.setName(product.getName());
        product.setDescription(product.getDescription());
        product.setCategory(categoryRepository.findByCode(productReq.getCategory()).get());
        MessagesResponse ms = new MessagesResponse();
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        var shopOptional = shopRepository.findByUserEmail(email);
        if (shopOptional.isPresent()) {
            product.setShop(shopOptional.get());
            productRepository.save(product);
            productReq.productDetailReqList.forEach(productDetailReq -> {
                ProductDetail productDetail = productDetailMapper.toEntity(productDetailReq);
                productDetail.setProduct(product);
                productDetailRepository.save(productDetail);
            });

        } else {
            ms.code = 500;
            ms.message = " Internal Server Error!";
        }
        return ms;
    }




    @Override
    public MessagesResponse deleteById(long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            productRepository.deleteById(id);

        } catch (Exception ex) {
            ms.code = 500;
            ms.message = "Lỗi khi thao tác xóa sản phẩm. Vui lòng thử lại!";
        }
        return ms;
    }

    @Override
    public MessagesResponse getById(long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            Product product = productRepository.findById(id).get();
        ms.data =  product.getProductDetailList().stream().map(productDetail ->
              new ProductRes(product.getId(),product.getName(),product.getDescription(),productDetail.getOriginPrice(),
                      productDetail.getPrice(),product.getCategory().getCode(),productDetail.getColor().getCode(),productDetail.getSize().getCode(),productDetail.getInStock(),productDetail.getSoldQuantity())).collect(Collectors.toList());


        } catch (Exception ex) {
            ms.code = 404;
            ms.message = "Item Not Found!";
        }
        return  ms;
    }

    @Override
    public PageDTO<ProductDetailResponse> getLists(ApiParameter apiParameter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductDetail> criteriaQuery = criteriaBuilder.createQuery(ProductDetail.class);
        Root<ProductDetail> root = criteriaQuery.from(ProductDetail.class);
        List<Predicate> predicates = new ArrayList<>();

        // Filter by text (if provided)
        String searchText = "%" + apiParameter.filter.text + "%";
        Predicate nameLike = criteriaBuilder.like(root.get("name"), searchText);
        Predicate colorLike = criteriaBuilder.like(root.get("color"), searchText);
        Predicate sizeLike = criteriaBuilder.like(root.get("size"), searchText);
        Predicate priceLike = criteriaBuilder.equal(root.get("price"), apiParameter.filter.text);
        predicates.add(criteriaBuilder.or(nameLike, colorLike, sizeLike, priceLike));

        // Filter by created date (if provided)
        if (apiParameter.filter != null && apiParameter.filter.created != null) {
            predicates.add(criteriaBuilder.equal(root.get("created"), apiParameter.filter.created));
        }


        // Filter by author (if provided)
        if (apiParameter.filter != null && apiParameter.filter.author != null && !apiParameter.filter.author.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("author"), apiParameter.filter.author));
        }
        // Filter by descending and orderBy (if provided)
        if (apiParameter.filter != null && apiParameter.filter.orderBy!=null) {
            if (apiParameter.filter.ascending) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(apiParameter.filter.orderBy)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(apiParameter.filter.orderBy)));
            }
        }

        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }
        TypedQuery<ProductDetail> query = entityManager.createQuery(criteriaQuery);
        int totalRows = query.getResultList().size();
        List<ProductDetail> results = query
                .setFirstResult((apiParameter.page - 1) * apiParameter.limit) // Offset
                .setMaxResults(apiParameter.limit) // Limit
                .getResultList();
        PageDTO<ProductDetailResponse> responsePageDTO = new PageDTO<>(productDetailResponseMapper.toDTOList(results), apiParameter.page, totalRows);

        return responsePageDTO;
    }
}
