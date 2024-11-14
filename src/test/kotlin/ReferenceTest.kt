import com.intellij.testFramework.fixtures.BasePlatformTestCase
import typespec.psi.interfaces.TsAliasStatement
import typespec.psi.interfaces.TsModelStatement

class ReferenceTest  : BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return "src/test/testData/reference"
    }

    fun testAliasReference() {
        val reference = myFixture.getReferenceAtCaretPosition("alias.tsp")
        val resolve = reference?.resolve()
        assertNotNull(resolve)
        assertInstanceOf(resolve, TsAliasStatement::class.java)
        assertEquals("\"test string\"", (resolve as TsAliasStatement).expression.text)
    }

    fun testModelReference() {
        val reference = myFixture.getReferenceAtCaretPosition("model.tsp")
        assertNotNull(reference)
        val resolve = reference?.resolve()
        assertInstanceOf(resolve, TsModelStatement::class.java)
    }
}