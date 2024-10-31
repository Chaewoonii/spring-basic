package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // MemberRepository는 인터페이스이며, 구현체를 필요로 함.
    // 구현체 없이 로직을 실행(save, findById)하려고 하면 NullPointException이 발생함. 따라서 구현체를 생성해야함 -> MemoryMemberRepository
    // MemoryMemberRepository 구현체를 생성함으로써 MemberServiceImpl은 MemberService 인터페이스와 MemoryMemberRepository 두 개의 클래스를 의존하게 됨.
    // DIP 원칙을 위반: 역할(인터페이스)이 아닌 구현(구현클래스)에 의존
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
