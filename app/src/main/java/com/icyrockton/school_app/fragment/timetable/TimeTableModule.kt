package com.icyrockton.school_app.fragment.timetable

import org.koin.dsl.module


val timeTableModule= module {


    single { TimeTableRepository(get()) }
}