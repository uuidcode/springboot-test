package org.springframework.data.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.springframework.data.domain.Sort.Order;

public class PageImplTest {
    private List<Integer> totalList = IntStream.range(0, 125)
        .mapToObj(Integer::valueOf)
        .collect(Collectors.toList());

    public List<Integer> getList(Pageable pageable) {
        return this.totalList
            .stream()
            .skip(pageable.getOffset())
            .limit(pageable.getPageSize())
            .collect(Collectors.toList());
    }

    @Test
    public void first() {
        Pageable pageable = new PageRequest(0, 10);
        PageImpl page = new PageImpl(this.getList(pageable), pageable, totalList.size());

        assertThat(page.getTotalPages()).isEqualTo(13);
        assertThat(page.getTotalElements()).isEqualTo(125);
        assertThat(page.getNumberOfElements()).isEqualTo(10);
        assertThat(page.hasNext()).isTrue();
        assertThat(page.hasPrevious()).isFalse();
        assertThat(page.isFirst()).isTrue();
        assertThat(page.isLast()).isFalse();
        assertThat(pageable.hasPrevious()).isFalse();
    }

    @Test
    public void last() {
        Sort sort = new Sort(new Order(ASC, "projectId"));
        Pageable pageable = new PageRequest(12, 10, sort);
        PageImpl page = new PageImpl(this.getList(pageable), pageable, totalList.size());

        assertThat(page.hasNext()).isFalse();
        assertThat(page.hasPrevious()).isTrue();
        assertThat(page.isLast()).isTrue();
        assertThat(page.isFirst()).isFalse();
        assertThat(page.getTotalElements()).isEqualTo(125);
        assertThat(page.getNumberOfElements()).isEqualTo(5);
        assertThat(pageable.getOffset()).isEqualTo(120);
        assertThat(pageable.hasPrevious()).isTrue();
    }
}
