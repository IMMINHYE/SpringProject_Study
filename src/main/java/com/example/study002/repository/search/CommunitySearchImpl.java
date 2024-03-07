package com.example.study002.repository.search;

import com.example.study002.domain.community.Community;
import com.example.study002.domain.community.QCommunity;
import com.example.study002.domain.community.QCommunityReply;
import com.example.study002.dto.community.CommunityListReplyCountDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.util.List;

public class CommunitySearchImpl extends QuerydslRepositorySupport implements CommunitySearch {

    public CommunitySearchImpl() {
        super(Community.class);
    }

    @Override
    public Page<Community> search1(Pageable pageable) {
        QCommunity community = QCommunity.community;
        JPQLQuery<Community> query = this.from(community);
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.or(community.title.contains("1"));
        booleanBuilder.or(community.content.contains("1"));

        query.where(new Predicate[]{booleanBuilder});
        query.where(new Predicate[]{community.bno.gt(0L)});

        this.getQuerydsl().applyPagination(pageable, query);

        List<Community> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<Community> searchAll(String[] types, String keyword, Pageable pageable) {
        QCommunity community = QCommunity.community;
        JPQLQuery<Community> query = this.from(community);

        if (types != null && types.length > 0 && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            String[] var1 = types;
            int var2 = types.length;
            for (int i = 0; i < var2; ++i) {
                switch (var1[i]) {
                    case "t":
                        booleanBuilder.or(community.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(community.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(community.writer.contains(keyword));
                }
            } //end for
            query.where(new Predicate[]{booleanBuilder});
        }// end if
        query.where(community.bno.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);
        List<Community> list = query.fetch();
        Long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<CommunityListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable) {
        QCommunity community = QCommunity.community;
        QCommunityReply reply = QCommunityReply.communityReply;

        JPQLQuery<Community> query = this.from(community);
        query.leftJoin(reply).on(reply.community.eq(community));
        query.groupBy(community);

        if (types != null && types.length > 0 && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (int i = 0; i < types.length; ++i) {
                switch (types[i]) {
                    case "t":
                        booleanBuilder.or(community.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(community.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(community.writer.contains(keyword));
                }
            } //end for
            query.where(new Predicate[]{booleanBuilder});
        }// end if
        query.where(community.bno.gt(0L));
        JPQLQuery<CommunityListReplyCountDTO> dtoQuery =
                query.select(Projections.bean(CommunityListReplyCountDTO.class,
                        community.bno,
                        community.title,
                        community.content,
                        community.writer,
                        community.regDate,
                        community.visitcount,
                        reply.count().as("replyCount")));

        this.getQuerydsl().applyPagination(pageable, query);
        List<CommunityListReplyCountDTO> dtoList = dtoQuery.fetch();
        Long count = query.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }
}
