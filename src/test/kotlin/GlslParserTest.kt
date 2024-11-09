import com.intellij.testFramework.ParsingTestCase
import typespec.language.TsParser


class TsParserTest : ParsingTestCase("", "test", TsParser()) {

    override fun getTestDataPath(): String {
        return "src/test/testData/parser"
    }

    override fun skipSpaces(): Boolean {
        return true
    }

    override fun includeRanges(): Boolean {
        return true
    }

    fun testParserFile() {
        doTest(true)
    }
}
