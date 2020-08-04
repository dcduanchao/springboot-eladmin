package me.zhengjie.oracle.primary.demo.repository;

import me.zhengjie.oracle.primary.demo.domain.TestOracle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ Author     ：duanchao
 * @ Date       ： 14:01 2020/7/28
 * @ Description：
 */
public interface TestOracleRepository extends JpaRepository<TestOracle, String>, JpaSpecificationExecutor<TestOracle> {
}