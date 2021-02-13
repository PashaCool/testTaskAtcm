package com.ataccama.model

data class ColumnDefinition(val columnName:String, val columnType: String, val isNullable:Boolean,
val isAutoIncrement:Boolean, var columnSize:String)
