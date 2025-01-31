package com.ddokddak.activityRecord.service;

import com.ddokddak.activityRecord.dto.StatsActivityRecordRequest;
import com.ddokddak.activityRecord.dto.StatsActivityRecordResult;
import com.ddokddak.activityRecord.dto.ActivityRecordResponse;
import com.ddokddak.activityRecord.mapper.ActivityRecordMapper;
import com.ddokddak.activityRecord.repository.ActivityRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ActivityRecordReadService {

    private final ActivityRecordRepository activityRecordRepository;

    public Boolean existsByMemberIdAndStartedAtBetween(Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt) {
        var result = activityRecordRepository.existsByMemberIdAndStartedAtBetween(memberId, fromStartedAt, toStartedAt);
        return result;
    }

    public List<StatsActivityRecordResult> statsByMemberIdAndPeriod(StatsActivityRecordRequest request, Long memberId) {
        List<StatsActivityRecordResult> result = activityRecordRepository.statsByMemberIdAndPeriodGroupByCategory(request, memberId);
        return result;
    }

    public List<ActivityRecordResponse> findByMemberIdAndStartedAtBetween(Long memberId, LocalDateTime fromStartedAt, LocalDateTime toStartedAt) {
        var activityRecords = activityRecordRepository.findByMemberIdAndStartedAtBetween( memberId, fromStartedAt, toStartedAt );
        return activityRecords.stream().map(ActivityRecordMapper::toActivityRecordResponse).collect(Collectors.toList());
    }
}
