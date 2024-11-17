import com.intellij.testFramework.fixtures.BasePlatformTestCase
import typespec.psi.interfaces.TsAliasStatement
import typespec.psi.interfaces.TsEnumStatement
import typespec.psi.interfaces.TsModelStatement
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
        assertEquals("var", resolve.name)
    }

    fun testEnumReference() {
        val reference = myFixture.getReferenceAtCaretPosition("enum.tsp")
        val resolve = reference?.resolve()
        assertInstanceOf(resolve, TsEnumStatement::class.java)
        assertEquals("Direction", (resolve as TsEnumStatement).name)
    }

    fun testScalarReference() {
        val reference = myFixture.getReferenceAtCaretPosition("scalar.tsp")
        val resolve = reference?.resolve()
        assertInstanceOf(resolve, TsScalarStatement::class.java)
        assertEquals("Password", (resolve as TsScalarStatement).name)
    }

    fun testModelReference() {
        val reference = myFixture.getReferenceAtCaretPosition("model.tsp")
        val resolve = reference?.resolve()
        assertInstanceOf(resolve, TsModelStatement::class.java)
        assertEquals("Animal", (resolve as TsModelStatement).name)
    }

    fun testImportReference() {
        myFixture.configureByFiles("import1.tsp")
        val reference = myFixture.getReferenceAtCaretPosition("import/import2.tsp")
        val resolve = reference?.resolve()
        assertInstanceOf(resolve, TsModelStatement::class.java)
        assertEquals("Animal", (resolve as TsModelStatement).name)
    }

    fun testImportRecursiveReference() {
        val reference = myFixture.getReferenceAtCaretPosition("import/import3.tsp")
        val resolve = reference?.resolve()
        assertNull(resolve)
    }
}