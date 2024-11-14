import com.intellij.testFramework.fixtures.BasePlatformTestCase
import typespec.psi.interfaces.TsAliasStatement
import typespec.psi.interfaces.TsEnumStatement
import typespec.psi.interfaces.TsInterfaceStatement
import typespec.psi.interfaces.TsModelStatement
import typespec.psi.interfaces.TsNamespaceStatement
import typespec.psi.interfaces.TsOperationStatement
import typespec.psi.interfaces.TsScalarStatement

class ReferenceTest : BasePlatformTestCase() {

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

    fun testEnumReference() {
        val reference = myFixture.getReferenceAtCaretPosition("enum.tsp")
        val resolve = reference?.resolve()
        assertInstanceOf(resolve, TsEnumStatement::class.java)
    }

    fun testScalarReference() {
        val reference = myFixture.getReferenceAtCaretPosition("scalar.tsp")
        val resolve = reference?.resolve()
        assertInstanceOf(resolve, TsScalarStatement::class.java)
    }

    fun testModelReference() {
        val reference = myFixture.getReferenceAtCaretPosition("model.tsp")
        val resolve = reference?.resolve()
        assertInstanceOf(resolve, TsModelStatement::class.java)
    }
}