package hello.core.member;

import java.util.Map;
import java.util.HashMap;

public class MemoryMemberRepository implements MemberRepository {

    // 실무에서는 동시성 이슈 때문에 ConcurrentHashMap 을 쓴다.
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
