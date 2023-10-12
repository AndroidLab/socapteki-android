package ru.apteka.components.data.navigation_manager

import androidx.navigation.NavController


/**
 * Описывает свойства и методы менеджера навигации.
 */
interface INavigationManager {

    /**
     * Возвращает главный навигационный контроллер.
     */
    val generalNavController: NavController

    /**
     * Скрывает toolBar.
     */
    fun hideToolbar()

    /**
     * Показывает toolBar.
     */
    fun showToolbar()

}