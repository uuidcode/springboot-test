package com.github.uuidcode.springboot.test.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.uuidcode.springboot.test.entity.Author;
import com.github.uuidcode.springboot.test.entity.Project;
import com.github.uuidcode.springboot.test.entity.QAuthor;
import com.github.uuidcode.springboot.test.entity.QProjectAuthorMap;

@Service
@Transactional
public class AuthorService extends CoreService<Author> {
    private QAuthor qAuthor = QAuthor.author;
    private QProjectAuthorMap qProjectAuthorMap = QProjectAuthorMap.projectAuthorMap;

    public List<Author> findAll(Project project) {
        return
            this.query()
            .select(qAuthor, qProjectAuthorMap)
            .from(qProjectAuthorMap)
            .join(qAuthor).on(qAuthor.authorId.eq(qProjectAuthorMap.authorId))
            .where(qProjectAuthorMap.projectId.eq(project.getProjectId()))
            .where(qAuthor.status.eq(Author.Status.SERVICE))
            .fetch()
            .stream()
            .map(t -> t.get(qAuthor).setProjectAuthorMap(t.get(qProjectAuthorMap)))
            .collect(Collectors.toList());
    }
}
