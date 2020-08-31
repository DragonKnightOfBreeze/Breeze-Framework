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

package com.windea.breezeframework.core.domain.text

//language=scss prefix=$color:

/**Sets of common-used system colors.*/
object Colors {
	const val aliceblue: String = "aliceblue"
	const val antiquewhite: String = "antiquewhite"
	const val aqua: String = "aqua"
	const val aquamarine: String = "aquamarine"
	const val azure: String = "azure"
	const val beige: String = "beige"
	const val bisque: String = "bisque"
	const val black: String = "black"
	const val blanchedalmond: String = "blanchedalmond"
	const val blue: String = "blue"
	const val blueviolet: String = "blueviolet"
	const val brown: String = "brown"
	const val burlywood: String = "burlywood"
	const val cadetblue: String = "cadetblue"
	const val chartreuse: String = "chartreuse"
	const val chocolate: String = "chocolate"
	const val coral: String = "coral"
	const val cornflowerblue: String = "cornflowerblue"
	const val cornsilk: String = "cornsilk"
	const val crimson: String = "crimson"
	const val cyan: String = "cyan"
	const val darkblue: String = "darkblue"
	const val darkcyan: String = "darkcyan"
	const val darkgoldenrod: String = "darkgoldenrod"
	const val darkgray: String = "darkgray"
	const val darkgreen: String = "darkgreen"
	const val darkgrey: String = "darkgrey"
	const val darkkhaki: String = "darkkhaki"
	const val darkmagenta: String = "darkmagenta"
	const val darkolivegreen: String = "darkolivegreen"
	const val darkorange: String = "darkorange"
	const val darkorchid: String = "darkorchid"
	const val darkred: String = "darkred"
	const val darksalmon: String = "darksalmon"
	const val darkseagreen: String = "darkseagreen"
	const val darkslateblue: String = "darkslateblue"
	const val darkslategray: String = "darkslategray"
	const val darkslategrey: String = "darkslategrey"
	const val darkturquoise: String = "darkturquoise"
	const val darkviolet: String = "darkviolet"
	const val deeppink: String = "deeppink"
	const val deepskyblue: String = "deepskyblue"
	const val dimgray: String = "dimgray"
	const val dimgrey: String = "dimgrey"
	const val dodgerblue: String = "dodgerblue"
	const val firebrick: String = "firebrick"
	const val floralwhite: String = "floralwhite"
	const val forestgreen: String = "forestgreen"
	const val fuchsia: String = "fuchsia"
	const val gainsboro: String = "gainsboro"
	const val ghostwhite: String = "ghostwhite"
	const val gold: String = "gold"
	const val goldenrod: String = "goldenrod"
	const val gray: String = "gray"
	const val green: String = "green"
	const val greenyellow: String = "greenyellow"
	const val grey: String = "grey"
	const val honeydew: String = "honeydew"
	const val hotpink: String = "hotpink"
	const val indianred: String = "indianred"
	const val indigo: String = "indigo"
	const val ivory: String = "ivory"
	const val khaki: String = "khaki"
	const val lavender: String = "lavender"
	const val lavenderblush: String = "lavenderblush"
	const val lawngreen: String = "lawngreen"
	const val lemonchiffon: String = "lemonchiffon"
	const val lightblue: String = "lightblue"
	const val lightcoral: String = "lightcoral"
	const val lightcyan: String = "lightcyan"
	const val lightgoldenrodyellow: String = "lightgoldenrodyellow"
	const val lightgray: String = "lightgray"
	const val lightgreen: String = "lightgreen"
	const val lightgrey: String = "lightgrey"
	const val lightpink: String = "lightpink"
	const val lightsalmon: String = "lightsalmon"
	const val lightseagreen: String = "lightseagreen"
	const val lightskyblue: String = "lightskyblue"
	const val lightslategray: String = "lightslategray"
	const val lightslategrey: String = "lightslategrey"
	const val lightsteelblue: String = "lightsteelblue"
	const val lightyellow: String = "lightyellow"
	const val lime: String = "lime"
	const val limegreen: String = "limegreen"
	const val linen: String = "linen"
	const val magenta: String = "magenta"
	const val maroon: String = "maroon"
	const val mediumaquamarine: String = "mediumaquamarine"
	const val mediumblue: String = "mediumblue"
	const val mediumorchid: String = "mediumorchid"
	const val mediumpurple: String = "mediumpurple"
	const val mediumseagreen: String = "mediumseagreen"
	const val mediumslateblue: String = "mediumslateblue"
	const val mediumspringgreen: String = "mediumspringgreen"
	const val mediumturquoise: String = "mediumturquoise"
	const val mediumvioletred: String = "mediumvioletred"
	const val midnightblue: String = "midnightblue"
	const val mintcream: String = "mintcream"
	const val mistyrose: String = "mistyrose"
	const val moccasin: String = "moccasin"
	const val navajowhite: String = "navajowhite"
	const val navy: String = "navy"
	const val oldlace: String = "oldlace"
	const val olive: String = "olive"
	const val olivedrab: String = "olivedrab"
	const val orange: String = "orange"
	const val orangered: String = "orangered"
	const val orchid: String = "orchid"
	const val palegoldenrod: String = "palegoldenrod"
	const val palegreen: String = "palegreen"
	const val paleturquoise: String = "paleturquoise"
	const val palevioletred: String = "palevioletred"
	const val papayawhip: String = "papayawhip"
	const val peachpuff: String = "peachpuff"
	const val peru: String = "peru"
	const val pink: String = "pink"
	const val plum: String = "plum"
	const val powderblue: String = "powderblue"
	const val purple: String = "purple"
	const val red: String = "red"
	const val rosybrown: String = "rosybrown"
	const val royalblue: String = "royalblue"
	const val saddlebrown: String = "saddlebrown"
	const val salmon: String = "salmon"
	const val sandybrown: String = "sandybrown"
	const val seagreen: String = "seagreen"
	const val seashell: String = "seashell"
	const val sienna: String = "sienna"
	const val silver: String = "silver"
	const val skyblue: String = "skyblue"
	const val slateblue: String = "slateblue"
	const val slategray: String = "slategray"
	const val slategrey: String = "slategrey"
	const val snow: String = "snow"
	const val springgreen: String = "springgreen"
	const val steelblue: String = "steelblue"
	const val tan: String = "tan"
	const val teal: String = "teal"
	const val thistle: String = "thistle"
	const val tomato: String = "tomato"
	const val turquoise: String = "turquoise"
	const val violet: String = "violet"
	const val wheat: String = "wheat"
	const val white: String = "white"
	const val whitesmoke: String = "whitesmoke"
	const val yellow: String = "yellow"
	const val yellowgreen: String = "yellowgreen"
}
