package com.vishnu.secretmessage.di


import com.vishnu.secretmessage.data.remote.PostService
import com.vishnu.secretmessage.repository.PostRepository
import com.vishnu.secretmessage.viewmodel.PostViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //    private const val BASE_URL = "http://127.0.0.1:5000/"
//    private const val BASE_URL = "http://10.0.2.2:5000/"
    private const val BASE_URL = "http://3.255.24.222/"

    @Singleton
    @Provides
    fun providePostApiService(): PostService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostService::class.java)
    }

    @Provides
    fun provideProductRepository(postService: PostService): PostRepository {
        return PostRepository(postService)
    }

    @Provides
    fun provideProductViewModel(repository: PostRepository): PostViewModel {
        return PostViewModel(repository)
    }
}
