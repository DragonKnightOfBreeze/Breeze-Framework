// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

/**数字的格式化类型。*/
enum class NumberFormatType {
	/**默认的格式化风格。*/
	Default,

	/**格式化为数字。 */
	Number,

	/**格式化为整数。*/
	Integer,

	/**格式化为百分比。 */
	Percent,

	/**格式化为货币。 */
	Currency,

	/**格式化为缩写数字。*/
	CompactNumber;
}
