package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StopWatch;

import com.example.demo.config.TestQueryDslConfig;
import com.example.demo.entity.Post;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestQueryDslConfig.class)
class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	// 첫 번째 페이지에서 데이터 개수가 pageSize(20)보다 적을 때
	@Test
	public void testFirstPageWithLessThanPageSize() {
		Pageable pageable = PageRequest.of(0, 20); // 첫 번째 페이지, 20개 조회

		StopWatch stopWatch1 = new StopWatch();
		stopWatch1.start();
		Page<Post> page1 = postRepository.findPostByPageImpl(pageable);
		stopWatch1.stop();
		System.out.println(" (20개 데이터) PageImpl 실행 시간: " + stopWatch1.getTotalTimeMillis() + "ms");

		StopWatch stopWatch2 = new StopWatch();
		stopWatch2.start();
		Page<Post> page2 = postRepository.findPostByPageableExecutionUtils(pageable);
		stopWatch2.stop();
		System.out.println(" (20개 데이터) PageableExecutionUtils 실행 시간: " + stopWatch2.getTotalTimeMillis() + "ms");

		assertEquals(page1.getTotalElements(), page2.getTotalElements());
	}

	//  마지막 페이지일 가능성이 있는 경우
	@Test
	public void testLastPagePossibility() {
		Pageable pageable = PageRequest.of(1, 10); // 두 번째 페이지, 10개 조회 (마지막 페이지 예상)

		StopWatch stopWatch1 = new StopWatch();
		stopWatch1.start();
		Page<Post> page1 = postRepository.findPostByPageImpl(pageable);
		stopWatch1.stop();
		System.out.println(" (마지막 페이지) PageImpl 실행 시간: " + stopWatch1.getTotalTimeMillis() + "ms");

		StopWatch stopWatch2 = new StopWatch();
		stopWatch2.start();
		Page<Post> page2 = postRepository.findPostByPageableExecutionUtils(pageable);
		stopWatch2.stop();
		System.out.println(" (마지막 페이지) PageableExecutionUtils 실행 시간: " + stopWatch2.getTotalTimeMillis() + "ms");

		assertEquals(page1.getTotalElements(), page2.getTotalElements());
	}

	// ✅ 3. 전체 데이터가 많을 때 (1000개 이상일 때 성능 비교)
	@Test
	public void testPerformanceWithLargeDataSet() {
		Pageable pageable = PageRequest.of(0, 100); // 100개씩 조회

		StopWatch stopWatch1 = new StopWatch();
		stopWatch1.start();
		Page<Post> page1 = postRepository.findPostByPageImpl(pageable);
		stopWatch1.stop();
		System.out.println("🚀 (100개 데이터) PageImpl 실행 시간: " + stopWatch1.getTotalTimeMillis() + "ms");

		StopWatch stopWatch2 = new StopWatch();
		stopWatch2.start();
		Page<Post> page2 = postRepository.findPostByPageableExecutionUtils(pageable);
		stopWatch2.stop();
		System.out.println("🚀 (100개 데이터) PageableExecutionUtils 실행 시간: " + stopWatch2.getTotalTimeMillis() + "ms");

		assertEquals(page1.getTotalElements(), page2.getTotalElements());
	}

	// ✅ 4. 중간 페이지 (데이터가 1000개 이상일 때 500~600번째 데이터 조회)
	@Test
	public void testMiddlePagePerformance() {
		Pageable pageable = PageRequest.of(5, 100); // 500~600번째 데이터 조회

		StopWatch stopWatch1 = new StopWatch();
		stopWatch1.start();
		Page<Post> page1 = postRepository.findPostByPageImpl(pageable);
		stopWatch1.stop();
		System.out.println("🚀 (중간 페이지, 100개 데이터) PageImpl 실행 시간: " + stopWatch1.getTotalTimeMillis() + "ms");

		StopWatch stopWatch2 = new StopWatch();
		stopWatch2.start();
		Page<Post> page2 = postRepository.findPostByPageableExecutionUtils(pageable);
		stopWatch2.stop();
		System.out.println("🚀 (중간 페이지, 100개 데이터) PageableExecutionUtils 실행 시간: " + stopWatch2.getTotalTimeMillis() + "ms");

		assertEquals(page1.getTotalElements(), page2.getTotalElements());
	}

	// ✅ 5. 마지막 페이지 (데이터 1000개 중 마지막 100개 조회)
	@Test
	public void testLastPagePerformance() {
		Pageable pageable = PageRequest.of(9, 100); // 마지막 페이지 예상

		StopWatch stopWatch1 = new StopWatch();
		stopWatch1.start();
		Page<Post> page1 = postRepository.findPostByPageImpl(pageable);
		stopWatch1.stop();
		System.out.println("🚀 (마지막 페이지, 100개 데이터) PageImpl 실행 시간: " + stopWatch1.getTotalTimeMillis() + "ms");

		StopWatch stopWatch2 = new StopWatch();
		stopWatch2.start();
		Page<Post> page2 = postRepository.findPostByPageableExecutionUtils(pageable);
		stopWatch2.stop();
		System.out.println("🚀 (마지막 페이지, 100개 데이터) PageableExecutionUtils 실행 시간: " + stopWatch2.getTotalTimeMillis() + "ms");

		assertEquals(page1.getTotalElements(), page2.getTotalElements());
	}
}