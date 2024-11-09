package typespec.highlighting

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import typespec.language.TsIcon
import javax.swing.Icon

private val DESCRIPTORS = arrayOf(
    AttributesDescriptor("Comment // Multi-line", TsTextAttributes.MULTILINE_COMMENT_TEXT_ATTR),
    AttributesDescriptor("Comment // One-line", TsTextAttributes.COMMENT_TEXT_ATTR),
    AttributesDescriptor("Identifier", TsTextAttributes.VARIABLE_TEXT_ATTR),
    AttributesDescriptor("Builtin types", TsTextAttributes.BUILTIN_TYPE_TEXT_ATTR),
    AttributesDescriptor("Decorator", TsTextAttributes.DECORATOR_TEXT_ATTR),
    AttributesDescriptor("String", TsTextAttributes.STRING_TEXT_ATTR),
    AttributesDescriptor("Number", TsTextAttributes.NUMBERS_TEXT_ATTR),
    AttributesDescriptor("Keyword", TsTextAttributes.KEYWORD_TEXT_ATTR),
    AttributesDescriptor("Bad character", TsTextAttributes.BAD_CHARACTER_TEXT_ATTR),
)

private val ADDITIONAL_DESCRIPTORS = mutableMapOf(
    "v" to TsTextAttributes.VARIABLE_TEXT_ATTR,
    "d" to TsTextAttributes.DECORATOR_TEXT_ATTR,
    "b" to TsTextAttributes.BUILTIN_TYPE_TEXT_ATTR,
)

/**
 *
 */
class TsColorSettings : ColorSettingsPage {

    /**
    *
    */
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> {
        return DESCRIPTORS
    }

    /**
    *
    */
    override fun getColorDescriptors(): Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    /**
    *
    */
    override fun getDisplayName(): String {
        return "TypeSpec"
    }

    /**
    *
    */
    override fun getIcon(): Icon {
        return TsIcon.FILE
    }

    /**
    *
    */
    override fun getHighlighter(): SyntaxHighlighter {
        return TsSyntaxHighlighter()
    }

    /**
    *
    */
    override fun getDemoText(): String {
        return """
            import "@typespec/http";

            // One line comment
            using TypeSpec.Http;
            
            /**
              Multi
              Line
              Comment
            */
            model Store {
              name: <b>string</b>;
              address: Address;
            }
           
            @<d>route</d>("/stores")
            interface Stores {
              list(@<d>query</d> filter: <b>string</b>): Store[];
              read(@<d>path</d> id: Store): Store;
            }
            
            @<d>post</d>
            @<d>route</d>("/items/{product_id}")
            op addItemToWishlist(@<d>path</d> product_id: <b>string</b>): {
                @<d>statusCode</d> statusCode: 201;
            };
        """.trimIndent()
    }
    /**
    *
    */
    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey> {
        return ADDITIONAL_DESCRIPTORS
    }
}