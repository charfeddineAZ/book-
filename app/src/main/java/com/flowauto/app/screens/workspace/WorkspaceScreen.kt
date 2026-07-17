package com.flowauto.app.screens.workspace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.flowauto.app.models.workspace.FileItem
import com.flowauto.app.models.workspace.FileType

@Composable
fun WorkspaceScreen(navController: NavHostController) {
    val selectedFile = remember { mutableStateOf<FileItem?>(null) }
    val showCreateMenu = remember { mutableStateOf(false) }
    val files = remember {
        mutableStateOf(
            listOf(
                FileItem(name = "البيانات", type = FileType.FOLDER),
                FileItem(name = "test.json", type = FileType.JSON),
                FileItem(name = "config.js", type = FileType.JAVASCRIPT)
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // شريط علوي
        TopAppBar(
            title = { Text("مساحة العمل") },
            actions = {
                IconButton(onClick = { showCreateMenu.value = true }) {
                    Icon(Icons.Default.Add, contentDescription = "إنشاء")
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.CloudSync, contentDescription = "مزامنة")
                }
                IconButton(onClick = { navController.navigate("settings") }) {
                    Icon(Icons.Default.Settings, contentDescription = "إعدادات")
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
        ) {
            // شريط المسار
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .padding(8.dp)
            ) {
                Text("الملفات:", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(bottom = 8.dp))
                // قائمة الملفات
            }

            // محرر/عارض الملف
            if (selectedFile.value != null) {
                FileEditor(
                    modifier = Modifier.weight(1.5f),
                    file = selectedFile.value!!,
                    onUseInWorkflow = { navController.navigate("editor") }
                )
            } else {
                Box(
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Text("اختر ملف للتحرير")
                }
            }
        }

        // زر إنشاء جديد
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { showCreateMenu.value = true }
        ) {
            Icon(Icons.Default.Add, contentDescription = "إنشاء ملف")
        }

        // قائمة الإنشاء
        if (showCreateMenu.value) {
            CreateFileMenu(
                onDismiss = { showCreateMenu.value = false }
            )
        }
    }
}

@Composable
fun FileEditor(
    modifier: Modifier,
    file: FileItem,
    onUseInWorkflow: () -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(file.name, style = MaterialTheme.typography.titleSmall)
            Button(onClick = onUseInWorkflow) {
                Text("استخدام في Workflow")
            }
        }

        Card(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Text("محتوى الملف", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun CreateFileMenu(onDismiss: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("إنشاء عنصر جديد", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(16.dp))

            listOf(
                "مجلد" to Icons.Default.CreateNewFolder,
                "ملف نصي" to Icons.Default.NoteAdd,
                "JSON" to Icons.Default.Code,
                "CSV" to Icons.Default.TableChart,
                "JavaScript" to Icons.Default.Code,
                "Python" to Icons.Default.Code
            ).forEach { (label, icon) ->
                Button(
                    onClick = { onDismiss() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(label)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
