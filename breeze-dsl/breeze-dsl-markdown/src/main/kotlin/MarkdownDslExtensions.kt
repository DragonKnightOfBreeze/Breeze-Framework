/***********************************************************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 *
 *                                     ...]]]]]]..
 *                             ...,]OOOOOOOOOOOOOOO].
 *                            ./]/[[[[\OOOOOOOOOO@@@@O].
 *                                        .OOOOOO@@@@@@@@].
 *                                         =OOOOO@@@@@@@@@@@`.
 *                                          =@@OO@@@@@@@@@@@@@\.
 *                                          .\@@@@@@@@@@@@@@@@@@\.
 *                                   .. .    .O@@@@@@@@@@@@@@@@@@@@`.
 *                                    .``=\.  ,@@@@@@@^=@^O@@@@@@@@@@\.              .`  ..,]]]]]].
 *                      ... .....       .=OO` .O@@@@@^=^..O@@@@@@@@@@@.           .,@@]@@@@@@@@@@@.
 *               ........*[]],\OOOOO\]..  .\O^ O@@@@^O@`..@@@@@@@@@@@@@].      ....OO@@@@@@@@@@@@^
 *          ...*.......**[oOOOOOOOOO@@@@@`. =@`=@@@`=@@`.=@@@@@@@@@@@@@@\......./^.,@@@@@@@@@@@@^
 *                .........[\OOO@@@@@@@@@@@`]O@OO@`.=@@.`=@@@@@@@@@@@@@@@@^...,@`.=@@@@@@@@@@@@`
 *                            ......[[\OO@@@O\O@@`...@@@.@@@@@@@@O@@@@@@@@\..//..O@@@@@@O]`.\/.
 *                    ..,]/OOOO@@@@@@@@@@@^,`,`O@....@@\=@@OO@@@@@@@@@@@@@@]@^./@@@@@@@/`..OO@`
 *                 ..*...,]OO@@@@@@@@@@@O`.,,/@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/O@@@@@@@@@@@@@@@@@.
 *               ....*[*=o/\OO@@@@@@@@@O@@@@@@@@@@@`.=[\O@@@@@@@@@@@@@@@@@@@@@@@@@@@@O[=@@@@@@@O.
 *             ...*....*]/OOO@@@@@[.,/@@@@@@@@@@@@^...........[OOO@@@@@@@@@@@@@@@/.    .O@@@@@@@^
 *           ........**`,OO/oO/. ,/@@@@@@@@@@@@O`***..........**......*....*.=O[.        .=@@@@/\^
 *           *..   ,`.,`**,.. .,@@@@@@@@@@@@@@@^.**.**.....*`...*...../`.........         .\/[..=^.
 *           .    ..*\*. .. ./O@@@@@@@@@@@@@@@O^/....*....=@@@[[^*...=@OO..*... ..        =@@...@@^
 * \.            .*,.     .,O@@@@@@@@@@@@@@@O@O/^*...*.....\]*....**./=.o`*^.... ..      ,@@@@\=@@@`
 *  .`.          ..      ,OOO@@@@@@@OOO/[[[[..*........................**==.* ..         /@@@@@@@@@@.
 *    .,.               ,/`..            ... .........*................*.**.. ..       ./@@@@@@@@@@@.
 *       ,`            ./.               ..  .........*...=*`......../O...... ..     .O@@@@@@@@@@@@^
 *         ,..    ..   =.                ..   ...`...**...,**,**..][=@@`*=@@@@]]]\.]@@@@@@@@@@@@@@@^
 *    ..     .*.  ..   =.                ...,/@@@@@@@O^`..=*****.,\\*./ooO@@@@@@@@@@@@@@@@@@@@@/[...
 *   ..        .\.......`.                 .,O@@@@@@@@\O`.*****...*^`*O\OO@@@@@@@@@@@@@@@@@@@@/.
 *  ...    ..... .\.....\^..                 ./@@@@@@@O@@**......`...`=@O@@@@@@@@@@@@@@@@@@@`
 *   ...  ..       ....  ....          ....*./@@@@@@@@@@@O\.....*.....*@@@@@@@@@@@@@@@@@@@/.
 *   .... ..        ...`  ................*.=@@@@@@@@@@@@@@@\...`.....**@@@@@@@@@@@@@@@@@@@@`
 *    .......         ..,`.        ....*....[[O@@@@@@@@@@@@@@@\@@@\*.....\@@@@@@@@@@@@@@@@@@@@\.
 *     .......          ..,................./@/=@@@@@@@@@@@@@@@@@@@@@]...*\@@@@@@@@@@@@@@@@@@@@@\.
 *       ........           .*.   .........=@^*\/=@@@@@@@@@@@@@@@@@@@@@@\./@@@@@@@@@@@@@@@@@@@@@@@\.
 *      .. ........           .\.....**]/]/\]o\]]@@@@@@@@@@@@@@@@@@OOOO@@@@@@^/@@@@@@@@@@@@@@@@@@@@@`                 ..
 *       ...............    ......,/@@@@O@@Oo@@@@@@@@@@@@@@@@@@@@@@@@@@@@@=@@@O*[@@@@@@@@@@@@@@@@@@@@.               =O`
 *            .......*..........  .[@@O@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O@\..\@@@@@@@@@@@@@@@@@@@@.              /\]
 *   =`                    ,]`    ,/@@@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@O@@@@o@@OO\.,@@@@@@@@@@@@@@@@@@@@\.          ./O@O.
 *   =.       .O`        ,@@@@\.,@@@@@@@OO@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@o@@OO@O.=@@@@@@@@@@@@@@@@@@@@\.        ,O@@^=.
 * . .\.   =OO@.O.     ,@@@@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O/O@OOOOO`\@@@@@@@@@@@@@@@@@@@@O.     ./@@/=.=.
 * `  .,`. .@,\.O ....OOO@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O=..[\OOOOO`O@@@^ .,\@@@@@O@@@@@/\\]`./@@`. =^^
 * \`    ,\\/@@@`  ./@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/.=^...\oOOOO\@@@^          ./@@[`=@OO@@/.   .O.
 *  .\].     ./@@]]@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O`.......***OOOO@@@^        .,@@`  =/@@@/.    .OO.
 *      .[[[[`..\@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@O`..........**=OO@O@@`        /@@\]]/@@@@^O..  .OOO^
 *              .,O@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\*..........*,/@@@O@^]].    ./@@@@@OO@@@@o@@`  =OOO^
 *               .,O@@@@@@@@@@@@@@@@@@@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@Oo*......,O@@@OO^.\@@@].,@@@@@@@O@@@\/O=`  .=OO`.
 * .......`.   .\@@@@@@@@@@@@@@@@@@@@@@@@@/` =@@@@@@@@@@@@@@@@@@@@@@@@@@@@OO\]/@@@OOOO@@@@@@@@@\@@@@@@@@@@@`.    ,OO`.
 *   ,O`. ,O\].. ,@@@@@@@@@@@@@@@@@@@@@/.    ./@@@@@@@@@@@@@\@@OO@***O@@`[\@@O@@@@@@@O/`*@@@@`/@@@@@@@@@@^=O.  ..=O.
 *     .\OOO@O@@@O]`\@@@@@@@@@@@@@@@O\O\]..  .@@@@@@@@@@@@@@\OOo`OO@@@`..,O@@@@//\..[*....O@@@@@@@@@@@@@^./@.   ...
 *           ..[[O@@@@@@\]`. .....,@@@\/@@@@O/@@@@@@@@@.,@@\O@@\=O@@[.../@@@@[.=^.\.......==@@@@@@@@@^.  ,@O.
 *    .,]OO]`..     ..[O@@@@@@@O].. ./@@@@@@@@@@@@@@@@@^O@@@@OOOOoO`*,O@@@O`...=\..,\...,/./@@@@@@@@/.  .@`
 *  .O@@@/\O`...  ..,`.    .,\@@@@@@@@@@OOO@@@@@@@@@@@@\@O@@@@@OoOO@@@@@O......@@`..O..../@@@@@@@@O^.  ,O.
 *  .     .....,@@@O`            .[@@@@@@OOO@@@@@@@@@@@@\@@@@@@@@@@@@/[*......=@@@`...]@@@@O@@@@@/.  .=/.
 *          ..=@@@\.               .O@@OoooO@@@@@@@@@@@@@@@@OO\@OO**..........=@@@@@@@@@@`./@@@@\.  ./^
 *            .*O@@@\.              ....OOO@@@@@@@@@@@@@@@@@OO@@O@\]]]]]]]/@@@@@@@@@/[..../^,@@O\^ =@`
 *            ... .[\O^.          ./@@@@@@@@@@@@@@@@@@@@@@@@@@@@/,\@@@@@@@@@@@@@@]....../O`......,@O.
 *            ....                      ...\@@@@@@@@@@@@@@@@@@@`......[[`.@@@@@@@@/\OO[`......../@^
 *             ...                           .[\O^,@@@@@@@O@@@@..........=@@@@@@@@@`.........=@@@@].
 *             ...                            ...,O@OO@@@OOOO@@..........=@@@@@@@@@@^......,@@@@@@@^
 *             ..*.                           .,/OOO@@@@@OOOO@@`.........@@@@@@@@@@@@@@@@@@@@@@@@@@@\`..
 *             .....                          .OOOO@@@@OO@@@@@@@\......,@@@@@@@O@@@@@@@@@@@@@@@@@@@@@@@@O\]..
 *              ......                      ./OOOOOOO@@OOO@@@@@@@@@@@@@@@@@@@^.    ./@@@@@@@@@@@@@@@@@@@@@@@@@@O]`..
 *               ......                  .]OOOOOO[./@OOOOO@@@@@@@@@@@@@@@@@/.  .,/@@/`O@@@@@@@@@@@@@@@@@@@@O@@@@@@@@@@\]
 *               .........               .......*.,OOOOOO@@@@@@@@@@@@@@@@@@^.]@@@/.   .@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 *                ...........                 .../OOOOO//@@@@@@@@@@@@@@@@O@@@/`.       =@O@@@@@@@@@@@@@...[\@@@@@@@@@@@O
 *                 .........................*`.]OOOO/`.=@@@@@@@@@@@@@@@@@/\O.          .O/@@@@@@@@@@@O^         .[\O@@@@
 *                   .....................*.,[[[......=@@@`.\@@@@@@@@@@@@^.^            ,^,@@@@@@@@@@.                ..
 *                    .................*.............=@@@`./@@@@@@@@@@@@^ ..             .. \@@@@@@@@^`.         ...*[`.
 *                     ............................*,@@@^/@@@@@@@@@@@@@@.                    =@@@@@@@@..\...*[..
 *                      ...........................=@@@O@@@@@@@@@@@@@@@^                     .@@@@@@@@^. .\.
 *                        ..........................\/@@@@@@@\@@@@@@@@O.                ....*[,@@@@@@@^    .*.
 *                          ........................O@@@@@@/..@@@@@@@@^         ...**[.       .@@@@@@@@.     .,`.
 *                            ..................../@@@@@@@@\..@@@@@@@@^ ...*,[..               =@@@@@@@.        ,`.
 *                              ................/@@@@@@@`@@@^ =@@@@@@@^                        .\@@@@@@^          ,*.
 *                                  ..........,O@@@@@@` ..[O[`=@@@@@@@^                         ,@@@@@@^            .\.
 *                                          ,OO@@@@@/`.       .@@@@@@@O.                         @@@@@@\.             .\
 *                                      ..,/O@@@@@/.          .O@@@@@@@.                         .@@@@@^
 *                             ....*[`. ./OOOO@@/.             =@@@@@@O.                          =@@@@@.
 *                     ....*[..       .,OOOOOOO`               .@@@@@@@.                          =@@@@@^
 *
 * Breeze is blowing ...
 **********************************************************************************************************************/

@file:Suppress("unused")

package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.dsl.markdown.MarkdownDslConfig.emptyColumnText
import com.windea.breezeframework.dsl.markdown.MarkdownDslDefinitions.*
import com.windea.breezeframework.dsl.markdown.MarkdownDslDefinitions.List

@MarkdownDslMarker
inline fun markdownDsl(block: MarkdownDsl.() -> Unit) = MarkdownDsl().apply(block)

@MarkdownDslMarker
inline fun markdownDslConfig(block: MarkdownDslConfig.() -> Unit) = MarkdownDslConfig.apply(block)


@MarkdownDslMarker
fun InlineDslEntry.b(text: CharSequence) = BoldText(text)

@MarkdownDslMarker
fun InlineDslEntry.i(text: CharSequence) = ItalicText(text)

@MarkdownDslMarker
fun InlineDslEntry.s(text: CharSequence) = StrokedText(text)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.u(text: CharSequence) = UnderlinedText(text)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.em(text: CharSequence) = HighlightText(text)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.sup(text: CharSequence) = SuperscriptText(text)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.sub(text: CharSequence) = SubscriptText(text)

@MarkdownDslMarker
fun InlineDslEntry.icon(name: String) = Icon(name)

@MarkdownDslMarker
fun InlineDslEntry.footNote(reference: String) = FootNote(reference)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.autoLink(url: String) = AutoLink(url)

@MarkdownDslMarker
fun InlineDslEntry.link(name: String, url: String, title: String? = null) = InlineLink(name, url, title)

@MarkdownDslMarker
fun InlineDslEntry.image(name: String = "", url: String, title: String? = null) = InlineImageLink(name, url, title)

@MarkdownDslMarker
fun InlineDslEntry.refLink(reference: String, name: String? = null) = ReferenceLink(reference, name).toString()

@MarkdownDslMarker
fun InlineDslEntry.refImage(reference: String, name: String? = null) = ReferenceImageLink(reference, name).toString()

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.wikiLink(name: String, url: String) = WikiLink(name, url)

@MarkdownDslMarker
fun InlineDslEntry.code(text: String) = InlineCode(text)

@MarkdownDslMarker
fun InlineDslEntry.math(text: String) = InlineMath(text)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun MarkdownDsl.frontMatter(lazyText: () -> String) = FrontMatter(lazyText()).also { frontMatter = it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun MarkdownDsl.toc() = Toc().also { toc = it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun MarkdownDsl.abbr(reference: String, text: String) = Abbreviation(reference, text).also { references += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun MarkdownDsl.footNoteRef(reference: String, text: String) = FootNoteReference(reference, text).also { references += it }

@MarkdownDslMarker
fun MarkdownDsl.linkRef(reference: String, url: String, title: String? = null) = LinkReference(reference, url, title).also { references += it }

@MarkdownDslMarker
inline fun IDslEntry.textBlock(lazyText: () -> String) = TextBlock(lazyText()).also { content += it }

@MarkdownDslMarker
fun IDslEntry.mainHeading(text: String) = MainHeading(text).also { content += it }

@MarkdownDslMarker
fun IDslEntry.subHeading(text: String) = SubHeading(text).also { content += it }

@MarkdownDslMarker
fun IDslEntry.h1(text: String) = Heading1(text).also { content += it }

@MarkdownDslMarker
fun IDslEntry.h2(text: String) = Heading2(text).also { content += it }

@MarkdownDslMarker
fun IDslEntry.h3(text: String) = Heading3(text).also { content += it }

@MarkdownDslMarker
fun IDslEntry.h4(text: String) = Heading4(text).also { content += it }

@MarkdownDslMarker
fun IDslEntry.h5(text: String) = Heading5(text).also { content += it }

@MarkdownDslMarker
fun IDslEntry.h6(text: String) = Heading6(text).also { content += it }

@MarkdownDslMarker
fun IDslEntry.hr() = HorizontalLine.also { content += it }

@MarkdownDslMarker
inline fun IDslEntry.list(block: List.() -> Unit) = List().apply(block).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun IDslEntry.def(title: String, block: Definition.() -> Unit) = Definition(title).apply(block).also { content += it }

@MarkdownDslMarker
inline fun IDslEntry.table(block: Table.() -> Unit) = Table().apply(block).also { content += it }

@MarkdownDslMarker
inline fun IDslEntry.blockQueue(block: BlockQuote.() -> Unit) = BlockQuote().apply(block).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun IDslEntry.indentedBlock(block: IndentedBlock.() -> Unit) = IndentedBlock().apply(block).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun IDslEntry.sideBlock(block: SideBlock.() -> Unit) = SideBlock().apply(block).also { content += it }

@MarkdownDslMarker
inline fun IDslEntry.codeFence(language: String, lazyText: () -> String) = CodeFence(language, lazyText()).also { content += it }

@MarkdownDslMarker
inline fun IDslEntry.multilineMath(lazyText: () -> String) = MultilineMath(lazyText()).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun IDslEntry.admonition(
	qualifier: AdmonitionQualifier,
	title: String = "",
	type: AdmonitionType = AdmonitionType.Normal,
	block: Admonition.() -> Unit
) = Admonition(qualifier, title, type).apply(block).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun IDslEntry.import(url: String) = Import(url).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun IDslEntry.macros(name: String) = Macros(name).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun IDslEntry.macrosSnippet(name: String, block: MacrosSnippet.() -> Unit) = MacrosSnippet(name).apply(block).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
infix fun <T : WithAttributes> T.with(attributes: AttributeGroup) = apply { this.attributes = attributes }

@MarkdownDslMarker
inline fun List.ol(order: String, text: String, block: OrderedListNode.() -> Unit = {}) = OrderedListNode(order, text).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun List.ul(text: String, block: UnorderedListNode.() -> Unit = {}) = UnorderedListNode(text).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun List.task(status: Boolean, text: String, block: TaskListNode.() -> Unit = {}) = TaskListNode(status, text).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun ListNode.ol(order: String, text: String, block: OrderedListNode.() -> Unit = {}) = OrderedListNode(order, text).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun ListNode.ul(text: String, block: UnorderedListNode.() -> Unit = {}) = UnorderedListNode(text).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun ListNode.task(status: Boolean, text: String, block: TaskListNode.() -> Unit = {}) = TaskListNode(status, text).apply(block).also { nodes += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun Definition.node(title: String, block: DefinitionNode.() -> Unit) = DefinitionNode(title).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun Table.header(block: TableHeader.() -> Unit) = TableHeader().apply(block).also { header = it }

@MarkdownDslMarker
inline fun Table.row(block: TableRow.() -> Unit) = TableRow().apply(block).also { rows += it }

@MarkdownDslMarker
infix fun Table.columnSize(size: Int) = apply { columnSize = size }

@MarkdownDslMarker
fun TableHeader.column(text: String = emptyColumnText) = TableColumn(text).also { columns += it }

@MarkdownDslMarker
fun TableRow.column(text: String = emptyColumnText) = TableColumn(text).also { columns += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun TableRow.rowSpan() = column(">")

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun TableRow.colSpan() = column("^")

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.attributes(vararg attributes: Attribute) = AttributeGroup(attributes.toSet())

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.id(name: String) = IdAttribute(name)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.`class`(name: String) = ClassAttribute(name)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.prop(name: String, value: String) = PropertyAttribute(name to value)
