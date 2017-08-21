package org.springframework.data.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

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
        assertThat(page.hasNext()).isTrue();
        assertThat(page.isLast()).isFalse();
    }

    @Test
    public void last() {
        Pageable pageable = new PageRequest(12, 10);
        PageImpl page = new PageImpl(this.getList(pageable), pageable, totalList.size());

        assertThat(page.hasNext()).isFalse();
        assertThat(page.isLast()).isTrue();
    }
}
