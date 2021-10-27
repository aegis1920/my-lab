package com.bingbong.core;

import com.bingbong.core.member.Grade;
import com.bingbong.core.member.Member;
import com.bingbong.core.member.MemberService;
import com.bingbong.core.member.MemberServiceImpl;

public class MemberApp {
	public static void main(String[] args) {
		MemberService memberService = new MemberServiceImpl();
		Member memberA = new Member(1L, "memberA", Grade.VIP);
		memberService.join(memberA);
		
		Member byMember = memberService.findByMember(1L);
		
	}
}
