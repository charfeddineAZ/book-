package com.flowauto.app.screens.libraries

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.flowauto.app.models.libraries.*

@Composable
fun LibrariesScreen(navController: NavHostController) {
    val selectedTab = remember { mutableStateOf(0) }
    val selectedCategory = remember { mutableStateOf(0) }
    val showAddLibrary = remember { mutableStateOf(false) }
    val selectedLibrary = remember { mutableStateOf<LibraryItem?>(null) }
    val showLibraryEditor = remember { mutableStateOf(false) }

    val jsLibraries = remember {
        mutableStateOf(
            listOf(
                LibraryItem(
                    id = "1",
                    name = "استخراج الروابط",
                    type = LibraryType.JAVASCRIPT,
                    code = "return document.querySelectorAll('a').map(a => a.href);",
                    tags = listOf("استخراج", "روابط")
                ),
                LibraryItem(
                    id = "2",
                    name = "تعبئة النموذج",
                    type = LibraryType.JAVASCRIPT,
                    code = "// كود تعبئة نموذج",
                    tags = listOf("نموذج", "تعبئة")
                )
            )
        )
    }

    val pyLibraries = remember {
        mutableStateOf(
            listOf(
                LibraryItem(
                    id = "3",
                    name = "معالجة الصور",
                    type = LibraryType.PYTHON,
                    code = "from PIL import Image\n# معالجة الصور",
                    tags = listOf("صور", "معالجة")
                )
            )
        )
    }

    val raccords = remember {
        mutableStateOf(
            listOf(
                LibraryItem(
                    id = "4",
                    name = "تسجيل الدخول إلى Facebook",
                    type = LibraryType.RACCORD,
                    steps = listOf("فتح الموقع", "إدخال البيانات", "النقر على تسجيل الدخول"),
                    tags = listOf("facebook", "تسجيل دخول")
                )
            )
        )
    }

    val selectors = remember {
        mutableStateOf(
            listOf(
                LibraryItem(
                    id = "5",
                    name = "زر البحث",
                    type = LibraryType.SELECTOR,
                    selector = ".search-button",
                    tags = listOf("بحث", "زر")
                )
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
            title = { Text("المكتبات") },
            actions = {
                IconButton(onClick = { showAddLibrary.value = true }) {
                    Icon(Icons.Default.Add, contentDescription = "إضافة")
                }
                IconButton(onClick = { navController.navigate("settings") }) {
                    Icon(Icons.Default.Settings, contentDescription = "إعدادات")
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
        ) {
            // شريط البحث
            var searchQuery by remember { mutableStateOf("") }
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("ابحث في المكتبات...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            // تبويبات المكتبات
            TabRow(selectedTabIndex = selectedTab.value) {
                Tab(
                    selected = selectedTab.value == 0,
                    onClick = { selectedTab.value = 0 },
                    text = { Text("مكتبتي", style = MaterialTheme.typography.labelSmall) }
                )
                Tab(
                    selected = selectedTab.value == 1,
                    onClick = { selectedTab.value = 1 },
                    text = { Text("🏪 استكشاف", style = MaterialTheme.typography.labelSmall) }
                )
            }

            when (selectedTab.value) {
                0 -> MyLibrariesTab(
                    jsLibraries = jsLibraries.value,
                    pyLibraries = pyLibraries.value,
                    raccords = raccords.value,
                    selectors = selectors.value,
                    onLibrarySelected = {
                        selectedLibrary.value = it
                        showLibraryEditor.value = true
                    },
                    onLibraryDelete = { item ->
                        when (item.type) {
                            LibraryType.JAVASCRIPT -> jsLibraries.value = jsLibraries.value.filter { it.id != item.id }
                            LibraryType.PYTHON -> pyLibraries.value = pyLibraries.value.filter { it.id != item.id }
                            LibraryType.RACCORD -> raccords.value = raccords.value.filter { it.id != item.id }
                            LibraryType.SELECTOR -> selectors.value = selectors.value.filter { it.id != item.id }
                        }
                    }
                )
                1 -> CommunityHubTab()
            }
        }

        // زر إضافة عائم
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = { showAddLibrary.value = true }
        ) {
            Icon(Icons.Default.Add, contentDescription = "إضافة مكتبة")
        }

        // محرر المكتبة
        if (showLibraryEditor.value && selectedLibrary.value != null) {
            LibraryEditorPanel(
                library = selectedLibrary.value!!,
                onSave = { updated ->
                    when (updated.type) {
                        LibraryType.JAVASCRIPT -> jsLibraries.value = jsLibraries.value.map { if (it.id == updated.id) updated else it }
                        LibraryType.PYTHON -> pyLibraries.value = pyLibraries.value.map { if (it.id == updated.id) updated else it }
                        LibraryType.RACCORD -> raccords.value = raccords.value.map { if (it.id == updated.id) updated else it }
                        LibraryType.SELECTOR -> selectors.value = selectors.value.map { if (it.id == updated.id) updated else it }
                    }
                    showLibraryEditor.value = false
                },
                onDismiss = { showLibraryEditor.value = false }
            )
        }
    }
}

@Composable
fun MyLibrariesTab(
    jsLibraries: List<LibraryItem>,
    pyLibraries: List<LibraryItem>,
    raccords: List<LibraryItem>,
    selectors: List<LibraryItem>,
    onLibrarySelected: (LibraryItem) -> Unit,
    onLibraryDelete: (LibraryItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // JavaScript
        if (jsLibraries.isNotEmpty()) {
            item {
                LibrarySection(
                    title = "📜 JavaScript Library",
                    items = jsLibraries,
                    onItemSelected = onLibrarySelected,
                    onItemDelete = onLibraryDelete
                )
            }
        }

        // Python
        if (pyLibraries.isNotEmpty()) {
            item {
                LibrarySection(
                    title = "🐍 Python Library",
                    items = pyLibraries,
                    onItemSelected = onLibrarySelected,
                    onItemDelete = onLibraryDelete
                )
            }
        }

        // Raccords
        if (raccords.isNotEmpty()) {
            item {
                LibrarySection(
                    title = "🌐 Browser Connectors",
                    items = raccords,
                    onItemSelected = onLibrarySelected,
                    onItemDelete = onLibraryDelete
                )
            }
        }

        // Selectors
        if (selectors.isNotEmpty()) {
            item {
                LibrarySection(
                    title = "🎯 Selectors",
                    items = selectors,
                    onItemSelected = onLibrarySelected,
                    onItemDelete = onLibraryDelete
                )
            }
        }
    }
}

@Composable
fun LibrarySection(
    title: String,
    items: List<LibraryItem>,
    onItemSelected: (LibraryItem) -> Unit,
    onItemDelete: (LibraryItem) -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        items.forEach { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onItemSelected(item) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.labelSmall
                        )
                        if (item.tags.isNotEmpty()) {
                            Text(
                                text = item.tags.joinToString(", "),
                                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline),
                                maxLines = 1
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        IconButton(
                            onClick = { /* Test */ },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "اختبار",
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        IconButton(
                            onClick = { onItemDelete(item) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "حذف",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommunityHubTab() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                Icons.Default.Public,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "متجر المجتمع",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "اكتشف مكتبات مشاركة من المجتمع",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun LibraryEditorPanel(
    library: LibraryItem,
    onSave: (LibraryItem) -> Unit,
    onDismiss: () -> Unit
) {
    val name = remember { mutableStateOf(library.name) }
    val code = remember { mutableStateOf(library.code ?: "") }
    val tags = remember { mutableStateOf(library.tags.joinToString(",")) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxHeight(0.9f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TopAppBar(
                title = { Text("تحرير: ${library.name}") },
                actions = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "إغلاق")
                    }
                }
            )

            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("الاسم") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            TextField(
                value = code.value,
                onValueChange = { code.value = it },
                label = { Text("الكود") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(vertical = 8.dp),
                minLines = 8
            )

            TextField(
                value = tags.value,
                onValueChange = { tags.value = it },
                label = { Text("الوسوم (مفصولة بفواصل)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
                    Text("إلغاء")
                }
                Button(
                    onClick = {
                        onSave(
                            library.copy(
                                name = name.value,
                                code = code.value,
                                tags = tags.value.split(",").map { it.trim() }
                            )
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("حفظ")
                }
            }
        }
    }
}
