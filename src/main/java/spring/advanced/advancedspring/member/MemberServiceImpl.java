package spring.advanced.advancedspring.member;

import org.springframework.stereotype.Component;
import spring.advanced.advancedspring.member.annotation.ClassAop;
import spring.advanced.advancedspring.member.annotation.MethodAop;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {
    @Override
    @MethodAop("테스트값")
    public String hello(String name) {
        return "ok";
    }

    public String internal(String param) {
        return "ok";
    }
}
