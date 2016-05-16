package bowlingkata

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class BowlingGameShould {

    @Test fun `calculate score with bowls only`() {
        assertThat(score("23"), `is`(5))
    }

    @Test fun `calculate score with misses`() {
        assertThat(score("--"), `is`(0))
        assertThat(score("-1"), `is`(1))
        assertThat(score("1-2-3-4-5-6-7-8-9-"), `is`(45))
        assertThat(score("1425364253617-819-"), `is`(67))
    }

    @Test fun `calculate score with spares`() {
        assertThat(score("1/"), `is`(10))
        assertThat(score("1/-5"), `is`(15))
        assertThat(score("1/35"), `is`(21))
        assertThat(score("1/3/23"), `is`(30))
        assertThat(score("7/7/7/7/7/7/7/7/7/7/-"), `is`(163))
        assertThat(score("5/5/5/5/5/5/5/5/5/5--"), `is`(140))
        assertThat(score("5/5/5/5/5/5/5/5/5/5/5"), `is`(150))
    }

    @Test fun `calculate score with strikes`() {
        assertThat(score("X52"), `is`(24))
        assertThat(score("XX--"), `is`(30))
        assertThat(score("XX52"), `is`(49))
        assertThat(score("X--51"), `is`(16))
        assertThat(score("XXXXXXXXX---"), `is`(240))
        assertThat(score("XXXXXXXXXX--"), `is`(270))
        assertThat(score("XXXXXXXXXXX-"), `is`(290))
        assertThat(score("XXXXXXXXXXXX"), `is`(300))
    }

    @Test fun `calculate score with spares and strikes`() {
        assertThat(score("1/X1/1"), `is`(52))
        assertThat(score("1/35X"), `is`(31))

        assertThat(score("1/35XXX45"), `is`(103))
        assertThat(score("1/35XXX458/X35"), `is`(149))
        assertThat(score("1/35XXX458/X3/23"), `is`(160))
        assertThat(score("1/35XXX458/X3/XX6"), `is`(189))
    }
}
