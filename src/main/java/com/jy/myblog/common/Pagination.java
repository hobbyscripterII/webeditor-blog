package com.jy.myblog.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Pagination {
    private int start; // 페이지네이션 시작 번호
    private int end; // 페이지네이션 끝 번호
    private int realEnd; // 실제 끝 번호
    private boolean prev; // 이전 버튼
    private boolean next; // 다음 버튼
    private int page; // 페이지 번호(1 페이지, 2 페이지...)
    private int amount; // 한 페이지당 보여 줄 게시글 개수
    private int total; // 총 게시글 개수
    private int pageCnt = 5; // 페이지네이션 개수
    private Criteria criteria;

    public Pagination(Criteria criteria, int total) {
        this.page = criteria.getPage();
        this.amount = criteria.getAmount();
        this.total = total;
        this.criteria = criteria;

        // 1. 끝 페이지 계산: (int) Math.ceil(페이지 번호 / 페이지네이션 개수) * 페이지네이션 버튼 개수
        // (1) 1 / 5 = 0.2
        // (2) Math.ceil(0.2) * 5 = 5
        end = (int) Math.ceil(page / (double) pageCnt) * pageCnt;
        // 2. 시작 페이지 계산: 끝 페이지 번호 - 페이지네이션 버튼 개수 + 1
        // (1) 5 - 5 + 1 = 1
        start = end - pageCnt + 1;
        // 3. 실제 끝 번호 계산: (int) Math.ceil(전체 게시글 수 / 데이터 개수)
        // (1) 2 / 10 = 0.2
        // (2) Math.ceil(0.2) = 1
        realEnd = (int) Math.ceil(total / (double) amount);
        // 4. 끝 페이지 다시 계산
        // (1) 5 > 1 ? 1 : 5
        end = end > realEnd ? realEnd : end;
        // 5. 이전 버튼
        // start는 1, 11, 21... 식으로 증가되니 1보다 크면 true를 반환한다.
        prev = start > 1;
        // 6. 다음 버튼
        // next는 realEnd가 end보다 크면 true를 반환한다.
        next = realEnd > end;
    }

    // 페이징 처리
    @Getter
    @Setter
    public static class Criteria {
        private int subject; // 게시판 카테고리
        private int page; // 페이지 번호
        private int amount; // 한 페이지당 보여 줄 게시글 개수

        // 기본값 설정
        public Criteria() {
            page = 1;
            amount = 10;
        }

        public Criteria(int page, int amount) {
            super();
            this.page = page;
            this.amount = amount;
        }

        // limit
        public int getPageStart() {
            return (page - 1) * amount;
        }
    }
}