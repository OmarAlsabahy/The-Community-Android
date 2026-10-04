package com.tawajood.the_community_user.app.ui.screens.customerServices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tawajood.the_community_user.app.ui.shared.ComplaintCard
import com.tawajood.the_community_user.app.ui.shared.CustomScaffold
import com.tawajood.the_community_user.app.ui.shared.LoadingScreen
import com.tawajood.the_community_user.domain.models.customerServices.ComplaintResponseDto

@Composable
fun ComplaintsHistoryScreen(viewModel: ComplaintsHistoryViewModel = hiltViewModel()){
    val state by viewModel.state.collectAsState()
    CustomScaffold(hasTopBar = true, topBarTitle = "سجل الشكاوي", content = {paddingValues->
        Box(modifier = Modifier.fillMaxSize()){
            DisplayComplaints(Modifier.padding(horizontal = 16.dp).fillMaxSize().padding(paddingValues),state.complaintsHistory)
            LoadingScreen(state.isLoading)
        }
    })
}

@Composable
private fun DisplayComplaints(modifier: Modifier, complaints: List<ComplaintResponseDto>) {
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(complaints.size){index->
            ComplaintCard(complaints[index])
        }
    }
}