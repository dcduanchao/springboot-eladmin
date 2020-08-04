package me.zhengjie.mysql.second.entity.repository;

import me.zhengjie.mysql.second.entity.domain.PublicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author sheryl
* @date 2020-03-31
*/
public interface PublicationStatusRepository extends JpaRepository<PublicationStatus, Integer>, JpaSpecificationExecutor<PublicationStatus> {



}