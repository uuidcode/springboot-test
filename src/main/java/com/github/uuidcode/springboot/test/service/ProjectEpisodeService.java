package com.github.uuidcode.springboot.test.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.uuidcode.springboot.test.entity.Episode;

@Service
@Transactional
public class ProjectEpisodeService extends CoreService<Episode> {
    public void update(Episode episode) {
        this.update(
            episode::getEpisodeId,
            e ->
                e.setName(episode.getName())
                .setUpdatedDateTime(this.now())
        );
    }
}
