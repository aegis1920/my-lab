package com.bingbong.jpabook.jpashop.api;

import com.bingbong.jpabook.jpashop.domain.Member;
import com.bingbong.jpabook.jpashop.service.MemberService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable Long id,
        @RequestBody UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers(); //엔티티 -> DTO 변환
        List<MemberDto> collect = findMembers.stream()
            .map(m -> new MemberDto(m.getName()))
            .collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    class Result<T> {

        private T data;
    }

    @Data
    @AllArgsConstructor
    class MemberDto {

        private String name;
    }

    @Data
    static class UpdateMemberRequest {

        private String name;
    }

    @Data
    @AllArgsConstructor
    class UpdateMemberResponse {

        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {

        private String name; // 여기에 validation
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {

        private Long id;
    }
}
