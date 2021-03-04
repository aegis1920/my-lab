package com.bingbong.effectivejava.item50;

import java.util.Date;

public class Period {

    private final Date start;
    private final Date end;

    public Period(Date start, Date end) {
        // 방어적 복사 (새로운 객체에 넣음)
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        // 멀티스레드에서 validation을 통과하고 값에 넣어질 수 있기 때문에
        // 유효성 검사가 방어적 복사 뒤에 와야 한다.
        if (this.start.compareTo(this.end) > 0) {
            throw new IllegalArgumentException(start + "가 " + end + " 보다 늦을 수 없습니다.");
        }
    }
}
