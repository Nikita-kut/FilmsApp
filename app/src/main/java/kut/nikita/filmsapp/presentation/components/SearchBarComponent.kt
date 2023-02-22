package kut.nikita.filmsapp.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import kut.nikita.filmsapp.R
import kut.nikita.filmsapp.ui.theme.Shapes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
) {

    var isClearButtonVisible by remember { mutableStateOf(false) }
    val keyboardControl = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = searchText,
        onValueChange = {
            onSearchTextChanged.invoke(it)
            isClearButtonVisible = it.isNotEmpty()
        },
        placeholder = {
            Text(
                text = stringResource(id = R.string.film_search_placeholder),
                fontSize = 12.sp
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        trailingIcon = {
            AnimatedVisibility(visible = isClearButtonVisible, enter = fadeIn(), exit = fadeOut()) {
                IconButton(onClick = { onClearClick.invoke() }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "")
                }
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
            backgroundColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        maxLines = 1,
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = { keyboardControl?.hide() }),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        shape = Shapes.small,
    )
    BackHandler() {
        focusManager.clearFocus()
    }
}