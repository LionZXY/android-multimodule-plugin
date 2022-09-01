package ru.hh.plugins.geminio.sdk.template.executors

import com.android.tools.idea.wizard.template.RecipeExecutor
import ru.hh.plugins.geminio.sdk.recipe.models.commands.RecipeCommand
import ru.hh.plugins.geminio.sdk.template.mapping.expressions.evaluateBoolean
import ru.hh.plugins.geminio.sdk.template.models.GeminioRecipeExecutorData
import ru.hh.plugins.utils.notifications.Debug

internal fun RecipeExecutor.execute(
    command: RecipeCommand.Predicate,
    executorData: GeminioRecipeExecutorData
) = with(executorData) {
    val validIfExpression = command.validIf

    Debug.info("Predicate command [validIfExpression: $validIfExpression]")
    if (validIfExpression.evaluateBoolean(existingParametersMap)) {
        Debug.info("\tStart executing commands [validIf == true]")
        executeCommands(
            commands = command.commands,
            executorData = executorData
        )
    } else {
        // Skip predicate command
        Debug.info("\tSkip commands execution [validIf == false], try to execute 'elseCommands' if exists")
        executeCommands(
            commands = command.elseCommands,
            executorData = executorData
        )
    }
}
