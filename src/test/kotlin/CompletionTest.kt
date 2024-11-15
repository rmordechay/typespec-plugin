import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import typespec.psi.interfaces.TsAliasStatement
import typespec.psi.interfaces.TsEnumStatement
import typespec.psi.interfaces.TsInterfaceStatement
import typespec.psi.interfaces.TsModelStatement
import typespec.psi.interfaces.TsNamespaceStatement
import typespec.psi.interfaces.TsOperationStatement
import typespec.psi.interfaces.TsScalarStatement

class CompletionTest : BasePlatformTestCase() {

    override fun getTestDataPath(): String {
        return "src/test/testData/completion"
    }

    fun testCompletion() {
        myFixture.configureByFiles("Completion.tsp")
        myFixture.complete(CompletionType.BASIC)
        val lookupElementStrings = myFixture.lookupElementStrings
        assertNotNull(lookupElementStrings)
        assertContainsElements(lookupElementStrings!!, "string")
    }
}