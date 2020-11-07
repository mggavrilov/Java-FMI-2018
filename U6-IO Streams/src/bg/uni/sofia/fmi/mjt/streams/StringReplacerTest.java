package bg.uni.sofia.fmi.mjt.streams;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class StringReplacerTest {

	@Test
	public void testReal() {
		String input = "{real1} text1 {real2} text2 {real3} text3";
		StringReplacer replacer = new StringReplacer(input);

		assertEquals("new1 text1 new2 text2 new3 text3",
				replacer.replace(Map.of("real1", "new1", "real2", "new2", "real3", "new3")));
	}

	@Test
	public void testFake() {
		String input = "{fake1} text1 {fake2} text2 {fake3} text3";
		StringReplacer replacer = new StringReplacer(input);

		assertEquals("{fake1} text1 {fake2} text2 {fake3} text3",
				replacer.replace(Map.of("real1", "new1", "real2", "new2", "real3", "new3")));
	}

	@Test
	public void testNullInput() {
		String input = null;
		StringReplacer replacer = new StringReplacer(input);

		assertNull(replacer.replace(Map.of("real1", "new1", "real2", "new2", "real3", "new3")));
	}

	@Test
	public void testEmptyMap() {
		String input = "{real1} text1 {real2} text2 {real3} text3";
		StringReplacer replacer = new StringReplacer(input);

		assertEquals("{real1} text1 {real2} text2 {real3} text3", replacer.replace(Map.of()));
	}
}
