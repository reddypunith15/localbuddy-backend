package com.localbuddy.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CancellationRefundPolicyRepository extends JpaRepository<CancellationRefundPolicy, UUID> {

    @Query("""
            select policy
            from CancellationRefundPolicy policy
            where policy.active = true
              and policy.cancelledBy = :cancelledBy
              and :hoursBeforeStart >= policy.minHoursBeforeStart
              and (
                    policy.maxHoursBeforeStart is null
                    or :hoursBeforeStart < policy.maxHoursBeforeStart
                  )
            order by policy.minHoursBeforeStart desc
            """)
    List<CancellationRefundPolicy> findMatchingPolicies(
            @Param("cancelledBy") BookingCancellationActor cancelledBy,
            @Param("hoursBeforeStart") BigDecimal hoursBeforeStart
    );
}