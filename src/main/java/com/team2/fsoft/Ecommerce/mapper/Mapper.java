package com.team2.fsoft.Ecommerce.mapper;

import com.team2.fsoft.Ecommerce.dto.response.ReceiptRes;
import com.team2.fsoft.Ecommerce.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface Mapper<E,D> {
    E toEntity(D dto);
    D toDTO (E entity);

    List<D> toDTOList(List<E> entityList);
    List<E> toEntityList(List<D> dtoList);


}
