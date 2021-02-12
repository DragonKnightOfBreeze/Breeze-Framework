// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("MailExtensions")

package com.windea.breezeframework.springboot.extension

import org.springframework.mail.javamail.*

/**发送邮件。基于[MimeMessageHelper]。*/
@JvmOverloads
fun JavaMailSender.sendEmail(encoding: String? = null, prepare: MimeMessageHelper.() -> Unit) {
	try {
		this.send { MimeMessageHelper(it, encoding).prepare() }
	} catch(e: Exception) {
		e.printStackTrace()
	}
}

/**发送邮件。基于[MimeMessageHelper]。*/
@JvmOverloads
fun JavaMailSender.sendEmail(multipart: Boolean, encoding: String? = null, prepare: MimeMessageHelper.() -> Unit) {
	try {
		this.send { MimeMessageHelper(it, multipart, encoding).prepare() }
	} catch(e: Exception) {
		e.printStackTrace()
	}
}

/**发送邮件。基于[MimeMessageHelper]。*/
@JvmOverloads
fun JavaMailSender.sendEmail(multipartMode: Int, encoding: String? = null, prepare: MimeMessageHelper.() -> Unit) {
	try {
		this.send { MimeMessageHelper(it, multipartMode, encoding).prepare() }
	} catch(e: Exception) {
		e.printStackTrace()
	}
}
