package com.shoshin.restaurant.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.shoshin.restaurant.common.images.GlideImageLoader
import com.shoshin.restaurant.common.images.interfaces.ImageLoader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ImageLoaderModule {

    @Provides
    fun providePicasso(@ApplicationContext context: Context): Picasso =
        Picasso.Builder(context).build()

    @Provides
    fun provideGlide(@ApplicationContext context: Context): RequestManager =
        Glide.with(context)

    @Provides
    fun provideImageLoader(glide: RequestManager): ImageLoader = GlideImageLoader(glide)
}