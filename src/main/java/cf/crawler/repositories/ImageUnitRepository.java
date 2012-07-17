package cf.crawler.repositories;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;

import cf.crawler.domains.ImageUnit;

public interface ImageUnitRepository extends 
	AtomicOperationsRepository<ImageUnit, BigInteger> {
	
	List<ImageUnit> findByRecruitedAtGreaterThan(Date date, Sort sort);
}
